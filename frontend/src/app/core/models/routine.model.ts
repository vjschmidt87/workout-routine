export interface RoutineExercise {
  id?: number;
  exerciseKey: string;
  sets: number;
  reps: number;
  weightKg: number | null;
  restSeconds: number;
  sortOrder: number;
}

export interface Routine {
  id: number;
  name: string;
  description: string | null;
  active: boolean;
  createdAt: string;
  updatedAt: string;
  exercises: RoutineExercise[];
}

export interface RoutineRequest {
  name: string;
  description: string | null;
  exercises: Omit<RoutineExercise, 'id'>[];
}
