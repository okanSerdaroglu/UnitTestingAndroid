package com.example.unittestingandroid.ui.note;

import static com.example.unittestingandroid.repository.NoteRepository.NOTE_TITLE_NULL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.unittestingandroid.models.Note;
import com.example.unittestingandroid.repository.NoteRepository;
import com.example.unittestingandroid.ui.Resource;
import com.example.unittestingandroid.util.DateUtil;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class NoteViewModel extends ViewModel {

    private static final String TAG = "NoteViewModel";
    public static final String NO_CONTENT_ERROR = "Can't save note no content";

    public enum ViewState {VIEW, EDIT}

    // inject
    private final NoteRepository noteRepository;

    private MutableLiveData<Note> note = new MutableLiveData<>();
    private MutableLiveData<ViewState> viewState = new MutableLiveData<>();
    private boolean isNewNote;
    private Subscription updateSubscription;
    private Subscription insertSubscription;

    @Inject
    public NoteViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public LiveData<Resource<Integer>> insertNote() throws Exception {
        return LiveDataReactiveStreams.fromPublisher(  // converts flowable to live data
                noteRepository.insertNote(note.getValue())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                                insertSubscription = subscription;
                            }
                        })
        );
    }

    public LiveData<Note> observeNote() {
        return note;
    }

    public LiveData<ViewState> observeViewState() {
        return viewState;
    }

    public void setViewState(ViewState viewState) {
        this.viewState.setValue(viewState);
    }

    public void setIsNewNote(boolean isNewNote) {
        this.isNewNote = isNewNote;
    }

    public LiveData<Resource<Integer>> saveNote() throws Exception {

        if (!shouldAllowSave()) {
            throw new Exception(NO_CONTENT_ERROR);
        }
        cancelPendingTransactions();

        return new NoteInsertUpdateHelper<Integer>() {
            @Override
            public void setNoteId(int noteId) {
                isNewNote = false;
                Note currentNote = note.getValue();
                currentNote.setId(noteId);
                note.setValue(currentNote);
            }

            @Override
            public LiveData<Resource<Integer>> getAction() throws Exception {
                if (isNewNote) {
                    return insertNote();
                } else {
                    return updateNote();
                }
            }

            @Override
            public String defineAction() {
                if (isNewNote) {
                    return ACTION_INSERT;
                } else {
                    return ACTION_UPDATE;
                }
            }

            @Override
            public void onTransactionComplete() {
                updateSubscription = null;
                insertSubscription = null;
            }
        }.getAsLiveData();
    }

    private boolean shouldAllowSave() throws Exception {
        try {
            return removeWhiteSpace(note.getValue().getContent()).length() > 0;
        } catch (NullPointerException e) {
            throw new Exception(NO_CONTENT_ERROR);
        }

    }

    private void cancelPendingTransactions() {
        if (insertSubscription != null) {
            cancelInsertTransaction();
        }

        if (updateSubscription != null) {
            cancelUpdateTransaction();
        }
    }

    private void cancelUpdateTransaction() {
        updateSubscription.cancel();
        updateSubscription = null;
    }

    private void cancelInsertTransaction() {
        insertSubscription.cancel();
        insertSubscription = null;
    }

    public LiveData<Resource<Integer>> updateNote() throws Exception {
        return LiveDataReactiveStreams.fromPublisher(
                noteRepository.updateNote(note.getValue())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                                updateSubscription = subscription;
                            }
                        })
        );
    }

    public void updateNote(String title, String content) throws Exception {
        if (title == null || title.equals("")) {
            throw new NullPointerException("Title can't be null");
        }
        String temp = removeWhiteSpace(content);
        if (temp.length() > 0) {
            Note updatedNote = new Note(note.getValue());
            updatedNote.setTitle(title);
            updatedNote.setContent(content);
            updatedNote.setTimeStamp(DateUtil.getCurrentTimeStamp());

            note.setValue(updatedNote);
        }
    }

    private String removeWhiteSpace(String string) {
        string = string.replace("\n", "");
        string = string.replace(" ", "");
        return string;
    }


    public void setNote(Note note) throws Exception {
        if (note.getTitle() == null
                || note.getTitle().equals("")) {
            throw new Exception(NOTE_TITLE_NULL);
        }
        this.note.setValue(note);
    }

    public boolean shouldNavigateBack() {
        if (viewState.getValue() == ViewState.VIEW) {
            return true;
        } else {
            return false;
        }
    }

}
