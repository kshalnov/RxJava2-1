package ru.gb.course1.myapplication

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.UUID

class NoteRepoImpl : NoteRepo {
    private val cache: MutableList<NoteEntity> = mutableListOf()
    private val behaviorSubject = BehaviorSubject.createDefault<List<NoteEntity>>(cache)

    override fun add(note: NoteEntity) {
        cache.add(note.copy(id = UUID.randomUUID().toString()))
        behaviorSubject.onNext(cache)
    }

    override val notes: Observable<List<NoteEntity>>
        get() = behaviorSubject

}