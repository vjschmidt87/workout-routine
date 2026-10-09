import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../core/services/api.service';
import { TranslationService } from '../../core/services/translation.service';
import { Routine } from '../../core/models/routine.model';

@Component({
  selector: 'app-routines',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './routines.component.html',
  styleUrl: './routines.component.scss'
})
export class RoutinesComponent implements OnInit {
  private readonly api = inject(ApiService);
  protected readonly tr = inject(TranslationService);

  routines = signal<Routine[]>([]);
  loading = signal(true);

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.api.getRoutines().subscribe({
      next: r => { this.routines.set(r); this.loading.set(false); },
      error: () => this.loading.set(false)
    });
  }

  activate(routine: Routine): void {
    this.api.activateRoutine(routine.id).subscribe(() => this.load());
  }

  delete(routine: Routine): void {
    if (!confirm(this.tr.t('routines.confirm-delete'))) return;
    this.api.deleteRoutine(routine.id).subscribe(() => this.load());
  }

  formatDate(iso: string): string {
    return new Date(iso).toLocaleDateString();
  }
}
