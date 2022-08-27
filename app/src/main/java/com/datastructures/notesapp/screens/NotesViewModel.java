package com.datastructures.notesapp.screens;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.datastructures.notesapp.database.DatabaseHelper;
import com.datastructures.notesapp.pojo.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesViewModel extends ViewModel {
    public MutableLiveData<List<Note>> _notesMutableData = new MutableLiveData<>();
    private DatabaseHelper db ;

//    public MutableLiveData<List<Note>> getNotes() {
//        return _notesMutableData;
//    }

    public void setNotes(Context context){
         db = new DatabaseHelper(context);
         _notesMutableData.setValue(db.getAllNotes());
    }

    /**
     * Inserting new note in db
     * and refreshing the list
     */
     public void createNote(String note, String details) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertNote(note , details);
        Log.d("insert" , String.valueOf(id));
        // get the newly inserted note from db
        Note n = db.getNote(id);

        if (n != null) {
            // adding new note to array list at 0 position
            List<Note> tArr = new ArrayList<>();
            tArr.addAll(_notesMutableData.getValue());
            tArr.add(0 , n);
            _notesMutableData.setValue( tArr);
            Log.d("not meow" , _notesMutableData.getValue().toString());

        }
     }

    /**
     * Updating note in db and updating
     * item in the list by its position
     */
     void updateNote(String note,String details, int position) {
        Note n = _notesMutableData.getValue().get(position);
        // updating note text
        n.setNote(note);
        n.setDetails(details);

        // updating note in db
        db.updateNote(n);

        _notesMutableData.getValue().set(position, n);
         // refreshing the list
     }

     /**
     * Deleting note from SQLite and removing the
     * item from the list by its position
     */
     void deleteNote(int position  ) {
        // deleting the note from db
        db.deleteNote(_notesMutableData.getValue().get(position));

        // removing the note from the list

         List<Note> tArr = new ArrayList<>();
         tArr.addAll(_notesMutableData.getValue());
         tArr.remove(position);
         _notesMutableData.setValue(tArr);
     }
}
