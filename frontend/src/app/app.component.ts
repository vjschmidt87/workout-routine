import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './core/services/auth.service';
import { NavComponent } from './shared/components/nav/nav.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavComponent],
  template: `
    <div class="app-layout" [class.with-nav]="auth.isLoggedIn()">
      @if (auth.isLoggedIn()) {
        <app-nav />
      }
      <main class="app-main">
        <router-outlet />
      </main>
    </div>
  `,
  styles: [`
    .app-layout {
      min-height: 100vh;
    }
    .app-layout.with-nav {
      padding-left: var(--nav-width);
    }
    @media (max-width: 768px) {
      .app-layout.with-nav {
        padding-left: 0;
        padding-bottom: var(--nav-height-mobile);
      }
    }
    .app-main {
      min-height: 100vh;
    }
  `]
})
export class AppComponent {
  protected readonly auth = inject(AuthService);
}
