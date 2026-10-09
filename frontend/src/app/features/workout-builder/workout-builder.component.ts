import { Component, inject, signal, computed, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { ApiService, EquipmentItem } from '../../core/services/api.service';
import { TranslationService } from '../../core/services/translation.service';
import { Exercise } from '../../core/models/exercise.model';
import { RoutineExercise } from '../../core/models/routine.model';
import { MuscleMapComponent } from '../../shared/components/muscle-map/muscle-map.component';
import { ExerciseCardComponent } from '../../shared/components/exercise-card/exercise-card.component';

interface BuilderExercise extends RoutineExercise {
  exercise?: Exercise;
}

@Component({
  selector: 'app-workout-builder',
  standalone: true,
  imports: [CommonModule, MuscleMapComponent, ExerciseCardComponent],
  templateUrl: './workout-builder.component.html',
  styleUrl: './workout-builder.component.scss'
})
export class WorkoutBuilderComponent implements OnInit {
  private readonly api = inject(ApiService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  protected readonly tr = inject(TranslationService);

  step = signal<1 | 2 | 3>(1);
  selectedMuscles = signal<string[]>([]);
  allExercises = signal<Exercise[]>([]);
  selectedExercises = signal<BuilderExercise[]>([]);
  useMyEquipment = signal(false);
  userEquipment = signal<EquipmentItem[]>([]);
  routineName = signal('');
  routineDescription = signal('');
  loadingExercises = signal(false);
  saving = signal(false);
  error = signal('');
  editingId: number | null = null;

  filteredExercises = computed(() => {
    const muscles = this.selectedMuscles();
    const equipment = this.useMyEquipment()
      ? [...new Set(this.userEquipment().map(e => e.category))]
      : [];
    return this.allExercises().filter(e => {
      const matchMuscle = !muscles.length || e.muscles.some(m => muscles.includes(m)) || e.secondaryMuscles.some(m => muscles.includes(m));
      const matchEquip = !equipment.length || e.equipment.some(eq => equipment.includes(eq)) || e.equipment.includes('BODYWEIGHT');
      return matchMuscle && matchEquip;
    });
  });

  isExerciseSelected(key: string): boolean {
    return this.selectedExercises().some(e => e.exerciseKey === key);
  }

  ngOnInit(): void {
    this.api.getEquipment().subscribe(eq => this.userEquipment.set(eq));

    const editId = this.route.snapshot.queryParamMap.get('edit');
    if (editId) {
      this.editingId = +editId;
      this.api.getRoutine(this.editingId).subscribe(routine => {
        this.routineName.set(routine.name);
        this.routineDescription.set(routine.description ?? '');
        this.api.getExercises().subscribe(all => {
          this.allExercises.set(all);
          const builderExercises: BuilderExercise[] = routine.exercises.map(re => ({
            ...re,
            exercise: all.find(e => e.key === re.exerciseKey)
          }));
          this.selectedExercises.set(builderExercises);
          this.step.set(3);
        });
      });
    }
  }

  toggleMuscle(muscle: string): void {
    this.selectedMuscles.update(ms => ms.includes(muscle) ? ms.filter(m => m !== muscle) : [...ms, muscle]);
  }

  goToStep2(): void {
    if (!this.selectedMuscles().length) { this.error.set(this.tr.t('builder.select-muscles-first')); return; }
    this.error.set('');
    this.loadingExercises.set(true);
    this.api.getExercises(this.selectedMuscles()).subscribe(ex => {
      this.allExercises.set(ex);
      this.loadingExercises.set(false);
      this.step.set(2);
    });
  }

  toggleExercise(exercise: Exercise): void {
    if (this.isExerciseSelected(exercise.key)) {
      this.selectedExercises.update(es => es.filter(e => e.exerciseKey !== exercise.key));
    } else {
      this.selectedExercises.update(es => [...es, {
        exerciseKey: exercise.key,
        sets: 3, reps: 10, weightKg: null, restSeconds: 60, sortOrder: es.length,
        exercise
      }]);
    }
  }

  goToStep3(): void {
    if (!this.selectedExercises().length) { this.error.set(this.tr.t('builder.select-exercises-first')); return; }
    this.error.set('');
    this.step.set(3);
  }

  updateField(key: string, field: keyof BuilderExercise, value: string): void {
    this.selectedExercises.update(es => es.map(e => e.exerciseKey === key ? { ...e, [field]: +value || value } : e));
  }

  save(): void {
    if (!this.routineName().trim()) { this.error.set('Please enter a routine name.'); return; }
    this.saving.set(true);
    this.error.set('');

    const req = {
      name: this.routineName().trim(),
      description: this.routineDescription().trim() || null,
      exercises: this.selectedExercises().map((e, i) => ({
        exerciseKey: e.exerciseKey, sets: e.sets, reps: e.reps,
        weightKg: e.weightKg, restSeconds: e.restSeconds, sortOrder: i
      }))
    };

    const obs = this.editingId
      ? this.api.updateRoutine(this.editingId, req)
      : this.api.createRoutine(req);

    obs.subscribe({
      next: () => this.router.navigate(['/routines']),
      error: () => { this.error.set(this.tr.t('common.error')); this.saving.set(false); }
    });
  }

  exerciseName(ex: BuilderExercise): string {
    if (ex.exercise) return this.tr.language() === 'pt-br' ? ex.exercise.namePtBr : ex.exercise.nameEn;
    return ex.exerciseKey;
  }
}
