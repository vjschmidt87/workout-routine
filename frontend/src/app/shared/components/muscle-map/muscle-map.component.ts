import { Component, Input, Output, EventEmitter, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';

export const MUSCLES = [
  'CHEST', 'SHOULDERS', 'TRICEPS', 'BICEPS', 'BACK', 'LATS',
  'FOREARMS', 'QUADS', 'HAMSTRINGS', 'GLUTES', 'CALVES',
  'ABS', 'OBLIQUES', 'TRAPS', 'NECK'
] as const;

export type Muscle = typeof MUSCLES[number];

@Component({
  selector: 'app-muscle-map',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './muscle-map.component.html',
  styleUrl: './muscle-map.component.scss'
})
export class MuscleMapComponent {
  @Input() set selectedMuscles(v: string[]) { this._selected.set(new Set(v)); }
  @Output() muscleToggle = new EventEmitter<string>();

  protected readonly _selected = signal<Set<string>>(new Set());
  protected readonly viewSide = signal<'front' | 'back'>('front');

  isFront = computed(() => this.viewSide() === 'front');

  isSelected(muscle: string): boolean {
    return this._selected().has(muscle);
  }

  toggle(muscle: string): void {
    this.muscleToggle.emit(muscle);
  }

  setView(side: 'front' | 'back'): void {
    this.viewSide.set(side);
  }
}
