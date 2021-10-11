package ru.gb.course1.myapplication

import io.reactivex.Observable

interface NoteRepo {
    fun add(note: NoteEntity)
    val notes: Observable<List<NoteEntity>>
}