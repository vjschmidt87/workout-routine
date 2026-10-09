import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Exercise } from '../models/exercise.model';
import { Routine, RoutineRequest } from '../models/routine.model';

export interface EquipmentItem {
  id?: number;
  name: string;
  category: string;
}

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);

  // ── Exercises ───────────────────────────────────────────────────────────────
  getExercises(muscles?: string[], equipment?: string[]): Observable<Exercise[]> {
    let params = new HttpParams();
    if (muscles?.length) params = params.set('muscles', muscles.join(','));
    if (equipment?.length) params = params.set('equipment', equipment.join(','));
    return this.http.get<Exercise[]>('/api/exercises', { params });
  }

  // ── Equipment ───────────────────────────────────────────────────────────────
  getEquipment(): Observable<EquipmentItem[]> {
    return this.http.get<EquipmentItem[]>('/api/equipment');
  }

  addEquipment(item: EquipmentItem): Observable<EquipmentItem> {
    return this.http.post<EquipmentItem>('/api/equipment', item);
  }

  deleteEquipment(id: number): Observable<void> {
    return this.http.delete<void>(`/api/equipment/${id}`);
  }

  // ── Routines ────────────────────────────────────────────────────────────────
  getRoutines(): Observable<Routine[]> {
    return this.http.get<Routine[]>('/api/routines');
  }

  getRoutine(id: number): Observable<Routine> {
    return this.http.get<Routine>(`/api/routines/${id}`);
  }

  createRoutine(req: RoutineRequest): Observable<Routine> {
    return this.http.post<Routine>('/api/routines', req);
  }

  updateRoutine(id: number, req: RoutineRequest): Observable<Routine> {
    return this.http.put<Routine>(`/api/routines/${id}`, req);
  }

  deleteRoutine(id: number): Observable<void> {
    return this.http.delete<void>(`/api/routines/${id}`);
  }

  activateRoutine(id: number): Observable<Routine> {
    return this.http.patch<Routine>(`/api/routines/${id}/activate`, {});
  }
}
