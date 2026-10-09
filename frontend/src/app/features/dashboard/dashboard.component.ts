import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../core/services/api.service';
import { AuthService } from '../../core/services/auth.service';
import { TranslationService } from '../../core/services/translation.service';
import { Routine } from '../../core/models/routine.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  private readonly api = inject(ApiService);
  protected readonly auth = inject(AuthService);
  protected readonly tr = inject(TranslationService);

  routines = signal<Routine[]>([]);
  activeRoutine = signal<Routine | null>(null);
  loading = signal(true);

  ngOnInit(): void {
    this.api.getRoutines().subscribe({
      next: routines => {
        this.routines.set(routines);
        this.activeRoutine.set(routines.find(r => r.active) ?? null);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }
}
