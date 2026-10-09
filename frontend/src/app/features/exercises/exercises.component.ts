import { Component, inject, signal, computed, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../core/services/api.service';
import { TranslationService } from '../../core/services/translation.service';
import { Exercise } from '../../core/models/exercise.model';
import { ExerciseCardComponent } from '../../shared/components/exercise-card/exercise-card.component';
import { MUSCLES } from '../../shared/components/muscle-map/muscle-map.component';

const EQUIPMENT_KEYS = ['BARBELL', 'DUMBBELLS', 'CABLES', 'MACHINE', 'PULL_UP_BAR', 'RESISTANCE_BANDS', 'BENCH', 'BODYWEIGHT'];

@Component({
  selector: 'app-exercises',
  standalone: true,
  imports: [CommonModule, ExerciseCardComponent],
  templateUrl: './exercises.component.html',
  styleUrl: './exercises.component.scss'
})
export class ExercisesComponent implements OnInit {
  private readonly api = inject(ApiService);
  protected readonly tr = inject(TranslationService);

  exercises = signal<Exercise[]>([]);
  search = signal('');
  selectedMuscles = signal<string[]>([]);
  selectedEquipment = signal<string[]>([]);
  loading = signal(true);

  readonly muscles = MUSCLES;
  readonly equipmentKeys = EQUIPMENT_KEYS;

  filtered = computed(() => {
    const q = this.search().toLowerCase();
    const ms = this.selectedMuscles();
    const eq = this.selectedEquipment();
    return this.exercises().filter(e => {
      const matchName = !q || e.nameEn.toLowerCase().includes(q) || e.namePtBr.toLowerCase().includes(q);
      const matchMuscle = !ms.length || e.muscles.some(m => ms.includes(m)) || e.secondaryMuscles.some(m => ms.includes(m));
      const matchEquip = !eq.length || e.equipment.some(eq2 => eq.includes(eq2));
      return matchName && matchMuscle && matchEquip;
    });
  });

  ngOnInit(): void {
    this.api.getExercises().subscribe({
      next: ex => { this.exercises.set(ex); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  toggleMuscle(m: string): void {
    this.selectedMuscles.update(ms => ms.includes(m) ? ms.filter(x => x !== m) : [...ms, m]);
  }

  toggleEquipment(eq: string): void {
    this.selectedEquipment.update(eqs => eqs.includes(eq) ? eqs.filter(x => x !== eq) : [...eqs, eq]);
  }

  clearFilters(): void {
    this.selectedMuscles.set([]);
    this.selectedEquipment.set([]);
    this.search.set('');
  }
}
