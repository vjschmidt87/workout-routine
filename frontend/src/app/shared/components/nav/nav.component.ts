import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { TranslationService } from '../../../core/services/translation.service';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.scss'
})
export class NavComponent {
  protected readonly auth = inject(AuthService);
  protected readonly tr = inject(TranslationService);

  readonly links = [
    { path: '/dashboard', icon: '⊞', key: 'nav.dashboard' },
    { path: '/workout-builder', icon: '⊕', key: 'nav.workout-builder' },
    { path: '/routines', icon: '☰', key: 'nav.routines' },
    { path: '/exercises', icon: '◉', key: 'nav.exercises' },
    { path: '/equipment', icon: '⊙', key: 'nav.equipment' },
  ];

  toggleLanguage(): void {
    this.tr.setLanguage(this.tr.language() === 'en' ? 'pt-br' : 'en');
  }
}
