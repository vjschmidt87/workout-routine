export interface Exercise {
  key: string;
  nameEn: string;
  namePtBr: string;
  muscles: string[];
  secondaryMuscles: string[];
  equipment: string[];
  difficulty: 'BEGINNER' | 'INTERMEDIATE' | 'ADVANCED';
  instructionsEn: string;
  instructionsPtBr: string;
  youtubeVideoId: string | null;
}
