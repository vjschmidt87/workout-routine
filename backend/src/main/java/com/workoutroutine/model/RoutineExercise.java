package com.workoutroutine.model;

import jakarta.persistence.*;

@Entity
@Table(name = "routine_exercises")
public class RoutineExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;

    @Column(nullable = false)
    private String exerciseKey;

    private int sets = 3;
    private int reps = 10;
    private Double weightKg;
    private int restSeconds = 60;
    private int sortOrder = 0;

    public RoutineExercise() {}

    public RoutineExercise(Routine routine, String exerciseKey, int sets, int reps, Double weightKg, int restSeconds, int sortOrder) {
        this.routine = routine;
        this.exerciseKey = exerciseKey;
        this.sets = sets;
        this.reps = reps;
        this.weightKg = weightKg;
        this.restSeconds = restSeconds;
        this.sortOrder = sortOrder;
    }

    public Long getId() { return id; }
    public Routine getRoutine() { return routine; }
    public String getExerciseKey() { return exerciseKey; }
    public void setExerciseKey(String exerciseKey) { this.exerciseKey = exerciseKey; }
    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    public int getRestSeconds() { return restSeconds; }
    public void setRestSeconds(int restSeconds) { this.restSeconds = restSeconds; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
}
