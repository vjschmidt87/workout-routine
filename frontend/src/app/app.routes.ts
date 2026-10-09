import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'login', loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent) },
  { path: 'signup', loadComponent: () => import('./features/auth/signup/signup.component').then(m => m.SignupComponent) },
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  {
    path: 'exercises',
    canActivate: [authGuard],
    loadComponent: () => import('./features/exercises/exercises.component').then(m => m.ExercisesComponent)
  },
  {
    path: 'workout-builder',
    canActivate: [authGuard],
    loadComponent: () => import('./features/workout-builder/workout-builder.component').then(m => m.WorkoutBuilderComponent)
  },
  {
    path: 'routines',
    canActivate: [authGuard],
    loadComponent: () => import('./features/routines/routines.component').then(m => m.RoutinesComponent)
  },
  {
    path: 'equipment',
    canActivate: [authGuard],
    loadComponent: () => import('./features/equipment/equipment.component').then(m => m.EquipmentComponent)
  },
  { path: '**', redirectTo: 'dashboard' }
];
