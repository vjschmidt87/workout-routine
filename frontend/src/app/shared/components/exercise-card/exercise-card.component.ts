import { Component, Input, Output, EventEmitter, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Exercise } from '../../../core/models/exercise.model';
import { TranslationService } from '../../../core/services/translation.service';

@Component({
  selector: 'app-exercise-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './exercise-card.component.html',
  styleUrl: './exercise-card.component.scss'
})
export class ExerciseCardComponent {
  @Input({ required: true }) exercise!: Exercise;
  @Input() selected = false;
  @Input() showAddButton = false;
  @Output() addExercise = new EventEmitter<Exercise>();

  protected readonly tr = inject(TranslationService);
  private readonly sanitizer = inject(DomSanitizer);

  expanded = signal(false);

  get name(): string {
    return this.tr.language() === 'pt-br' ? this.exercise.namePtBr : this.exercise.nameEn;
  }

  get instructions(): string {
    return this.tr.language() === 'pt-br' ? this.exercise.instructionsPtBr : this.exercise.instructionsEn;
  }

  get videoUrl(): SafeResourceUrl | null {
    if (!this.exercise.youtubeVideoId) return null;
    return this.sanitizer.bypassSecurityTrustResourceUrl(
      `https://www.youtube.com/embed/${this.exercise.youtubeVideoId}?rel=0&modestbranding=1`
    );
  }

  get difficultyClass(): string {
    return `difficulty--${this.exercise.difficulty.toLowerCase()}`;
  }

  toggleExpand(): void {
    this.expanded.update(v => !v);
  }

  onAdd(event: Event): void {
    event.stopPropagation();
    this.addExercise.emit(this.exercise);
  }
}
