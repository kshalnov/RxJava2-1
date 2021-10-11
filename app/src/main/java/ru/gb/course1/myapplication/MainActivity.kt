package ru.gb.course1.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.gb.course1.myapplication.databinding.ActivityMainBinding
import java.lang.RuntimeException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val noteRepo: NoteRepo = NoteRepoImpl()

    //    private var currentDisposable: Disposable? = null
//        set(value) {
//            field?.takeIf { !it.isDisposed }?.dispose()
//            field = value
//        }
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            val noteName = binding.inputNameEditText.text.toString()
            val note = NoteEntity("", noteName)
            it.postDelayed(3000L) {
                noteRepo.add(note)
            }
            binding.inputNameEditText.text.clear()
        }

        compositeDisposable.add(noteRepo.notes
            .map { notes ->
                notes.map { it.id }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listNotes -> binding.resultTextView.text = listNotes.toString() },
                { thr -> Toast.makeText(this, thr.message, Toast.LENGTH_SHORT).show() }
            )
        )
//        currentDisposable = noteRepo.notes.subscribe {
//            binding.resultTextView.text = it.toString()
//        }
    }


    override fun onDestroy() {
//        currentDisposable = null
        compositeDisposable.dispose()
        super.onDestroy()
    }
}