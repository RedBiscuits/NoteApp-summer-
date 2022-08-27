package com.datastructures.notesapp.screens.home;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.datastructures.notesapp.R;
import com.datastructures.notesapp.adapters.NotesAdapter;
import com.datastructures.notesapp.databinding.FragmentHomeBinding;
import com.datastructures.notesapp.pojo.Note;
import com.datastructures.notesapp.screens.MainActivity;
import com.datastructures.notesapp.screens.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NotesAdapter mAdapter;
    private RecyclerView recyclerView;
    private TextView noNotesView;
    private NotesViewModel viewModel;
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback;
    ItemTouchHelper itemTouchHelper;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        viewModel = new ViewModelProvider(getActivity()).get(NotesViewModel.class);
        recyclerView = view.findViewById(R.id.recycler_view);
        noNotesView = view.findViewById(R.id.empty_notes_view);

        viewModel._notesMutableData.observe(getActivity(), notes -> {
                    mAdapter.setNotesList(notes);
                }
        );

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view2 -> showNoteDialog(false, null, -1));

        mAdapter = new NotesAdapter(getContext());
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                2 , StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();

        setupItemTouchHelper();
    }

    /**
     * Shows alert dialog with EditText options to enter / edit
     * a note.
     * when shouldUpdate=true, it automatically displays old note and changes the
     * button text to UPDATE
     */
    private void showNoteDialog(final boolean shouldUpdate, final Note note, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View view = layoutInflaterAndroid.inflate(R.layout.note_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);

        final EditText inputNoteTitle = view.findViewById(R.id.note);
        final EditText inputNoteDetails = view.findViewById(R.id.note_details);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));

        if (shouldUpdate && note != null) {
            inputNoteTitle.setText(note.getNote());
            inputNoteDetails.setText(note.getDetails());

        }
        alertDialogBuilderUserInput
                .setCancelable(true)
                .setOnCancelListener(meow -> mAdapter.notifyDataSetChanged())
                .setPositiveButton(shouldUpdate ? "update" : "save", (dialogBox, id) -> {

                });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            // Show toast message when no text is entered
            if (TextUtils.isEmpty(inputNoteTitle.getText().toString())) {
                Toast.makeText(getContext(), "Enter note!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                alertDialog.dismiss();
            }

            // check if user updating note
            if (shouldUpdate && note != null) {
                // update note by it's id
                viewModel.updateNote(inputNoteTitle.getText().toString()
                        ,inputNoteDetails.getText().toString()
                        , position);
                mAdapter.notifyDataSetChanged();
            } else {
                // create new note
                viewModel.createNote(inputNoteTitle.getText().toString()
                        ,inputNoteDetails.getText().toString() );
                mAdapter.notifyDataSetChanged();

            }
            toggleEmptyNotes();
        });
    }

    /**
     * Toggling list and empty notes view
     */
    private void toggleEmptyNotes() {
        if (viewModel._notesMutableData.getValue().size()> 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }


    /*
     * Setting up swipes
     * Left -> Edit note
     * Right -> Delete note
     * */
    private void setupItemTouchHelper() {
        simpleItemTouchCallback= new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                if(swipeDir == ItemTouchHelper.LEFT) {
                    android.app.AlertDialog.Builder builder
                            = new android.app.AlertDialog.Builder(getContext());

                    builder.setTitle("Delete task");
                    builder.setMessage("Are you sure you want do delete this ?");
                    builder.setPositiveButton("Confirm", (dialogInterface, i) -> {
                                viewModel.deleteNote(position);
                                mAdapter.notifyDataSetChanged();
                                toggleEmptyNotes();
                            }
                    );
                    builder.setNegativeButton(android.R.string.cancel, (dialogInterface, i) ->
                            Toast.makeText(getContext() , "Canceled" , Toast.LENGTH_SHORT));

                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    showNoteDialog(true, mAdapter.getNotesList().get(position), position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                Drawable icon;
                ColorDrawable background;
                View itemView = viewHolder.itemView;
                int backgroundCornerOffset =21;

                if(dX > 0){
                    icon = ContextCompat.getDrawable(getContext(),R.drawable.edit);
                    background= new ColorDrawable(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                }else {
                    icon = ContextCompat.getDrawable(getContext(),R.drawable.delete);
                    background= new ColorDrawable(Color.RED);
                }

                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight())/2;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight())/2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();

                if(dX>0){//right
                    int iconLeft = itemView.getLeft() + iconMargin;
                    int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                    background.setBounds(itemView.getLeft(),itemView.getTop()
                            , itemView.getLeft() + ((int)dX) + backgroundCornerOffset,itemView.getBottom());
                }else if(dX<0){//left
                    int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);
                    background.setBounds(itemView.getRight() + ((int)dX) - backgroundCornerOffset,itemView.getTop()
                            , itemView.getRight() ,itemView.getBottom());

                }else{
                    background.setBounds(0,0,0,0);
                }
                background.draw(c);
                icon.draw(c);
            }
        };

        itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /*
     * Showing Search menu option
     * Used to filter notes
     * */
    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}