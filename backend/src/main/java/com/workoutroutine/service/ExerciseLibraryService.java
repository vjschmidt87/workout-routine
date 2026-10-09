package com.workoutroutine.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ExerciseLibraryService {

    public record Exercise(
            String key,
            String nameEn,
            String namePtBr,
            Set<String> muscles,
            Set<String> secondaryMuscles,
            Set<String> equipment,
            String difficulty,
            String instructionsEn,
            String instructionsPtBr,
            String youtubeVideoId
    ) {}

    private final List<Exercise> library;

    public ExerciseLibraryService() {
        this.library = buildLibrary();
    }

    public List<Exercise> getAll() { return library; }

    public List<Exercise> filter(List<String> muscles, List<String> equipment) {
        return library.stream()
                .filter(e -> muscles == null || muscles.isEmpty() || e.muscles().stream().anyMatch(muscles::contains) || e.secondaryMuscles().stream().anyMatch(muscles::contains))
                .filter(e -> equipment == null || equipment.isEmpty() || e.equipment().stream().anyMatch(equipment::contains) || e.equipment().contains("BODYWEIGHT"))
                .toList();
    }

    public Optional<Exercise> findByKey(String key) {
        return library.stream().filter(e -> e.key().equals(key)).findFirst();
    }

    private List<Exercise> buildLibrary() {
        List<Exercise> ex = new ArrayList<>();

        // ── PUSH: Chest ──────────────────────────────────────────────────────
        ex.add(new Exercise("bench-press", "Barbell Bench Press", "Supino com Barra",
                Set.of("CHEST"), Set.of("TRICEPS", "SHOULDERS"),
                Set.of("BARBELL", "BENCH"), "INTERMEDIATE",
                "Lie on a flat bench. Grip the bar slightly wider than shoulder-width. Lower to your chest, then press up explosively.",
                "Deite em um banco plano. Pegue a barra um pouco mais larga que a largura dos ombros. Abaixe até o peito e empurre para cima.",
                "rT7DgCr-3pg"));

        ex.add(new Exercise("incline-bench-press", "Incline Bench Press", "Supino Inclinado",
                Set.of("CHEST"), Set.of("TRICEPS", "SHOULDERS"),
                Set.of("BARBELL", "BENCH"), "INTERMEDIATE",
                "Set bench to 30-45°. Press bar from upper chest upward, keeping shoulder blades retracted.",
                "Ajuste o banco para 30-45°. Empurre a barra a partir do peito superior para cima, mantendo as escápulas retraídas.",
                "DbFgcCFnVJc"));

        ex.add(new Exercise("dumbbell-bench-press", "Dumbbell Bench Press", "Supino com Halteres",
                Set.of("CHEST"), Set.of("TRICEPS", "SHOULDERS"),
                Set.of("DUMBBELLS", "BENCH"), "BEGINNER",
                "Lie flat with a dumbbell in each hand. Press them up and together, lower under control.",
                "Deite com um halter em cada mão. Empurre para cima e junte-os, abaixe com controle.",
                "VmB1G1K7v94"));

        ex.add(new Exercise("push-up", "Push-Up", "Flexão de Braços",
                Set.of("CHEST"), Set.of("TRICEPS", "SHOULDERS"),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Start in a high plank. Lower your chest to the floor by bending elbows, then push back up.",
                "Comece em prancha alta. Abaixe o peito ao chão dobrando os cotovelos e empurre de volta.",
                "IODxDxX7oi4"));

        ex.add(new Exercise("cable-fly", "Cable Fly", "Crucifixo no Cabo",
                Set.of("CHEST"), Set.of("SHOULDERS"),
                Set.of("CABLES"), "INTERMEDIATE",
                "Set cables at shoulder height. Step forward, bring handles together in an arc in front of your chest.",
                "Ajuste os cabos na altura dos ombros. Dê um passo à frente e junte as alças em arco na frente do peito.",
                "Iwe6AmxU898"));

        ex.add(new Exercise("dips", "Chest Dips", "Paralelas para Peito",
                Set.of("CHEST"), Set.of("TRICEPS", "SHOULDERS"),
                Set.of("BODYWEIGHT"), "INTERMEDIATE",
                "Lean forward on parallel bars. Lower until elbows hit 90°, then push back up.",
                "Incline-se para frente nas barras paralelas. Abaixe até os cotovelos chegarem a 90°, depois empurre de volta.",
                "2z8JmcrW-As"));

        // ── PUSH: Shoulders ───────────────────────────────────────────────────
        ex.add(new Exercise("overhead-press", "Overhead Press", "Desenvolvimento com Barra",
                Set.of("SHOULDERS"), Set.of("TRICEPS", "TRAPS"),
                Set.of("BARBELL"), "INTERMEDIATE",
                "Stand with bar at shoulder height. Press overhead to full arm extension, then lower.",
                "Fique em pé com a barra na altura dos ombros. Pressione acima da cabeça até a extensão total dos braços.",
                "2yjwXTZQDDI"));

        ex.add(new Exercise("dumbbell-shoulder-press", "Dumbbell Shoulder Press", "Desenvolvimento com Halteres",
                Set.of("SHOULDERS"), Set.of("TRICEPS"),
                Set.of("DUMBBELLS"), "BEGINNER",
                "Sit or stand with dumbbells at shoulder height. Press overhead, bringing weights slightly together at the top.",
                "Sente ou fique em pé com halteres na altura dos ombros. Pressione acima da cabeça juntando levemente os pesos no topo.",
                "B-aVuyhvLHU"));

        ex.add(new Exercise("lateral-raise", "Lateral Raise", "Elevação Lateral",
                Set.of("SHOULDERS"), Set.of(),
                Set.of("DUMBBELLS"), "BEGINNER",
                "Hold dumbbells at your sides. Raise arms out to the sides until parallel to the floor.",
                "Segure halteres ao lado do corpo. Levante os braços para os lados até ficarem paralelos ao chão.",
                "3VcKaXpzqRo"));

        ex.add(new Exercise("face-pull", "Face Pull", "Face Pull",
                Set.of("SHOULDERS"), Set.of("TRAPS", "BACK"),
                Set.of("CABLES"), "BEGINNER",
                "Attach rope to high pulley. Pull toward your face, spreading the rope apart at the end of the movement.",
                "Conecte a corda na polia alta. Puxe em direção ao rosto, separando a corda no final do movimento.",
                "rep-x7_JTsI"));

        ex.add(new Exercise("arnold-press", "Arnold Press", "Arnold Press",
                Set.of("SHOULDERS"), Set.of("TRICEPS"),
                Set.of("DUMBBELLS"), "INTERMEDIATE",
                "Start with dumbbells in front of you, palms facing you. As you press up, rotate palms outward.",
                "Comece com halteres na frente, palmas voltadas para você. Ao pressionar, gire as palmas para fora.",
                "6Z15_WdXmVw"));

        // ── PUSH: Triceps ─────────────────────────────────────────────────────
        ex.add(new Exercise("tricep-pushdown", "Tricep Pushdown", "Tricep no Cabo",
                Set.of("TRICEPS"), Set.of(),
                Set.of("CABLES"), "BEGINNER",
                "Stand at cable machine with bar attachment. Keep upper arms fixed; extend forearms downward.",
                "Fique na máquina de cabo com barra. Mantenha os braços superiores fixos e estenda os antebraços para baixo.",
                "2-LAMcpzODU"));

        ex.add(new Exercise("skull-crusher", "Skull Crusher", "Rosca Francesa",
                Set.of("TRICEPS"), Set.of(),
                Set.of("BARBELL", "BENCH"), "INTERMEDIATE",
                "Lie on bench. Hold bar overhead with narrow grip. Lower bar toward forehead by bending elbows, then extend.",
                "Deite no banco. Segure a barra acima com pegada estreita. Abaixe em direção à testa dobrando os cotovelos.",
                "d_KZxkY_0cM"));

        ex.add(new Exercise("tricep-dips", "Tricep Dips", "Tríceps no Banco",
                Set.of("TRICEPS"), Set.of("CHEST"),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Place hands on bench behind you. Lower body by bending elbows, then push back up.",
                "Coloque as mãos no banco atrás de você. Abaixe o corpo dobrando os cotovelos e empurre de volta.",
                "0326dy_-CzM"));

        // ── PULL: Back ────────────────────────────────────────────────────────
        ex.add(new Exercise("deadlift", "Deadlift", "Levantamento Terra",
                Set.of("HAMSTRINGS", "GLUTES"), Set.of("BACK", "TRAPS", "FOREARMS"),
                Set.of("BARBELL"), "ADVANCED",
                "Stand with feet hip-width. Hinge at hips to grip bar. Drive hips forward to stand, keeping bar close to body.",
                "Fique com pés na largura do quadril. Incline-se nos quadris para segurar a barra. Empurre os quadris para frente para ficar de pé.",
                "op9kVnSso6Q"));

        ex.add(new Exercise("bent-over-row", "Bent-Over Row", "Remada Curvada",
                Set.of("BACK"), Set.of("LATS", "BICEPS"),
                Set.of("BARBELL"), "INTERMEDIATE",
                "Hinge forward at hips with flat back. Pull bar to lower chest, squeezing shoulder blades together.",
                "Incline-se para frente com as costas retas. Puxe a barra para o peito inferior, juntando as escápulas.",
                "vT2GjY_Umpw"));

        ex.add(new Exercise("cable-row", "Seated Cable Row", "Remada Sentada no Cabo",
                Set.of("BACK"), Set.of("LATS", "BICEPS"),
                Set.of("CABLES"), "BEGINNER",
                "Sit at cable row machine. Pull handle to your torso, squeezing shoulder blades. Slowly return.",
                "Sente na máquina de remada. Puxe a alça até o torso, contraindo as escápulas. Retorne lentamente.",
                "GZbfZ033f74"));

        ex.add(new Exercise("dumbbell-row", "Dumbbell Row", "Remada com Haltere",
                Set.of("BACK"), Set.of("LATS", "BICEPS"),
                Set.of("DUMBBELLS"), "BEGINNER",
                "Place one knee on bench for support. Pull dumbbell toward your hip, keeping elbow close to body.",
                "Apoie um joelho no banco. Puxe o halter em direção ao quadril, mantendo o cotovelo próximo ao corpo.",
                "pYcpY9mTUVw"));

        ex.add(new Exercise("t-bar-row", "T-Bar Row", "Remada T",
                Set.of("BACK"), Set.of("LATS", "BICEPS"),
                Set.of("BARBELL"), "INTERMEDIATE",
                "Straddle the bar, hinge at hips. Pull the bar toward your chest keeping elbows close.",
                "Posicione-se sobre a barra, incline-se. Puxe a barra em direção ao peito mantendo os cotovelos fechados.",
                "j8k6P_3WNeo"));

        // ── PULL: Lats ────────────────────────────────────────────────────────
        ex.add(new Exercise("pull-up", "Pull-Up", "Barra Fixa",
                Set.of("LATS"), Set.of("BICEPS", "BACK"),
                Set.of("PULL_UP_BAR"), "INTERMEDIATE",
                "Hang from bar with overhand grip. Pull body up until chin clears the bar, then lower with control.",
                "Pendure-se na barra com pegada pronada. Puxe o corpo até o queixo passar a barra, depois abaixe com controle.",
                "eGo4IYlbE5g"));

        ex.add(new Exercise("chin-up", "Chin-Up", "Barra com Pegada Supinada",
                Set.of("LATS"), Set.of("BICEPS", "BACK"),
                Set.of("PULL_UP_BAR"), "INTERMEDIATE",
                "Hang from bar with underhand grip. Pull up until chin clears the bar.",
                "Pendure-se na barra com pegada supinada. Puxe até o queixo passar a barra.",
                "Z_fJeN3bgV4"));

        ex.add(new Exercise("lat-pulldown", "Lat Pulldown", "Puxada na Polia",
                Set.of("LATS"), Set.of("BICEPS", "BACK"),
                Set.of("CABLES"), "BEGINNER",
                "Sit at pulldown machine. Pull bar to upper chest, lean back slightly, squeeze lats at bottom.",
                "Sente na máquina de puxada. Puxe a barra até o peito superior, incline-se ligeiramente e contraia o dorsal.",
                "CAwf7n6Luuc"));

        ex.add(new Exercise("straight-arm-pulldown", "Straight-Arm Pulldown", "Pulldown com Braço Estendido",
                Set.of("LATS"), Set.of("BACK"),
                Set.of("CABLES"), "BEGINNER",
                "Stand at high cable. With arms extended, push rope or bar down to your hips in a sweeping arc.",
                "Fique de pé na polia alta. Com braços estendidos, empurre a corda para baixo até os quadris em movimento de varredura.",
                "F1N5mWFGBJw"));

        // ── PULL: Biceps ──────────────────────────────────────────────────────
        ex.add(new Exercise("barbell-curl", "Barbell Curl", "Rosca Direta com Barra",
                Set.of("BICEPS"), Set.of("FOREARMS"),
                Set.of("BARBELL"), "BEGINNER",
                "Stand holding bar with underhand grip. Curl up keeping elbows stationary, then lower slowly.",
                "Fique em pé com a barra em pegada supinada. Faça a rosca mantendo os cotovelos fixos, depois abaixe lentamente.",
                "kwG2ipFRgfo"));

        ex.add(new Exercise("dumbbell-curl", "Dumbbell Curl", "Rosca com Haltere",
                Set.of("BICEPS"), Set.of("FOREARMS"),
                Set.of("DUMBBELLS"), "BEGINNER",
                "Stand with dumbbells. Curl one or both up with a supinating motion. Lower under control.",
                "Fique com halteres. Faça a rosca com um ou ambos com movimento de supinação. Abaixe com controle.",
                "ykJmrZ5v0Oo"));

        ex.add(new Exercise("hammer-curl", "Hammer Curl", "Rosca Martelo",
                Set.of("BICEPS"), Set.of("FOREARMS"),
                Set.of("DUMBBELLS"), "BEGINNER",
                "Hold dumbbells with neutral grip (palms facing each other). Curl up without rotating wrist.",
                "Segure halteres com pegada neutra (palmas uma para a outra). Faça a rosca sem girar o pulso.",
                "TwD-YGVP4Bk"));

        ex.add(new Exercise("preacher-curl", "Preacher Curl", "Rosca Concentrada",
                Set.of("BICEPS"), Set.of(),
                Set.of("BARBELL", "BENCH"), "BEGINNER",
                "Rest upper arms on preacher pad. Curl bar up to shoulder level, then lower fully.",
                "Apoie os braços no banco concentrado. Faça a rosca até o nível dos ombros, depois abaixe completamente.",
                "fIWP-FRFNU0"));

        // ── LEGS: Quads ───────────────────────────────────────────────────────
        ex.add(new Exercise("squat", "Barbell Squat", "Agachamento com Barra",
                Set.of("QUADS"), Set.of("GLUTES", "HAMSTRINGS"),
                Set.of("BARBELL"), "INTERMEDIATE",
                "Bar on upper traps. Feet shoulder-width apart. Squat down until thighs parallel, drive up through heels.",
                "Barra nos trapézios superiores. Pés na largura dos ombros. Agache até as coxas ficarem paralelas ao chão e empurre pelos calcanhares.",
                "ultWZbUMPL8"));

        ex.add(new Exercise("goblet-squat", "Goblet Squat", "Agachamento Goblet",
                Set.of("QUADS"), Set.of("GLUTES", "HAMSTRINGS"),
                Set.of("DUMBBELLS"), "BEGINNER",
                "Hold dumbbell at chest. Squat deep with elbows inside knees. Drive up through heels.",
                "Segure o halter no peito. Agache fundo com cotovelos dentro dos joelhos. Empurre pelos calcanhares.",
                "MeIiIdhvXT4"));

        ex.add(new Exercise("leg-press", "Leg Press", "Leg Press",
                Set.of("QUADS"), Set.of("GLUTES", "HAMSTRINGS"),
                Set.of("MACHINE"), "BEGINNER",
                "Sit in leg press machine. Place feet shoulder-width on platform. Press until knees are nearly extended.",
                "Sente na máquina de leg press. Coloque os pés na largura dos ombros na plataforma. Pressione até os joelhos ficarem quase estendidos.",
                "IZxyjW7MPJQ"));

        ex.add(new Exercise("lunge", "Walking Lunge", "Avanço",
                Set.of("QUADS"), Set.of("GLUTES", "HAMSTRINGS"),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Step forward into a lunge, lower back knee toward floor. Drive front heel to return and step forward.",
                "Dê um passo à frente em avanço, abaixe o joelho traseiro em direção ao chão. Empurre pelo calcanhar da frente para retornar.",
                "QOVaHwm-Q6U"));

        ex.add(new Exercise("bulgarian-split-squat", "Bulgarian Split Squat", "Agachamento Búlgaro",
                Set.of("QUADS"), Set.of("GLUTES", "HAMSTRINGS"),
                Set.of("DUMBBELLS", "BENCH"), "INTERMEDIATE",
                "Place rear foot on bench. Lower front leg into a deep lunge, keeping torso upright.",
                "Coloque o pé traseiro no banco. Abaixe a perna da frente em um avanço profundo, mantendo o torso ereto.",
                "hs_HxOc2HMM"));

        ex.add(new Exercise("leg-extension", "Leg Extension", "Extensão de Perna",
                Set.of("QUADS"), Set.of(),
                Set.of("MACHINE"), "BEGINNER",
                "Sit in leg extension machine. Extend legs until straight, squeeze quads at top, lower under control.",
                "Sente na máquina de extensão. Estenda as pernas até ficarem retas, contraia o quadrícep no topo e abaixe com controle.",
                "YyvSfVTuHAY"));

        // ── LEGS: Hamstrings & Glutes ─────────────────────────────────────────
        ex.add(new Exercise("romanian-deadlift", "Romanian Deadlift", "Levantamento Terra Romeno",
                Set.of("HAMSTRINGS"), Set.of("GLUTES", "BACK"),
                Set.of("BARBELL"), "INTERMEDIATE",
                "Hold bar at hips. Hinge forward keeping legs slightly bent and back flat. Feel hamstring stretch, then return.",
                "Segure a barra nos quadris. Incline-se para frente com pernas levemente dobradas e costas retas. Sinta o alongamento dos isquiotibiais.",
                "JCXUYuzwNrM"));

        ex.add(new Exercise("leg-curl", "Leg Curl", "Flexão de Perna",
                Set.of("HAMSTRINGS"), Set.of("GLUTES"),
                Set.of("MACHINE"), "BEGINNER",
                "Lie face down on machine. Curl legs up toward your glutes, then lower slowly.",
                "Deite de bruços na máquina. Dobre as pernas em direção aos glúteos, depois abaixe lentamente.",
                "Orxoqi0GsbU"));

        ex.add(new Exercise("hip-thrust", "Hip Thrust", "Elevação de Quadril",
                Set.of("GLUTES"), Set.of("HAMSTRINGS"),
                Set.of("BARBELL", "BENCH"), "INTERMEDIATE",
                "Upper back on bench, barbell over hips. Drive hips up until body is parallel to floor, squeeze glutes.",
                "Costas superiores no banco, barra sobre os quadris. Levante os quadris até o corpo ficar paralelo ao chão, contraia os glúteos.",
                "LM8XlTnqDg4"));

        ex.add(new Exercise("glute-bridge", "Glute Bridge", "Ponte de Glúteo",
                Set.of("GLUTES"), Set.of("HAMSTRINGS"),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Lie on back, knees bent. Drive hips up by squeezing glutes, hold briefly at top.",
                "Deite de costas com joelhos dobrados. Levante os quadris contraindo os glúteos, segure brevemente no topo.",
                "OUgsJ8-Vi0E"));

        ex.add(new Exercise("cable-kickback", "Cable Kickback", "Chute no Cabo",
                Set.of("GLUTES"), Set.of("HAMSTRINGS"),
                Set.of("CABLES"), "BEGINNER",
                "Attach ankle strap to low cable. Kick leg straight back, squeezing glute at top.",
                "Conecte a tornozeleira à polia baixa. Chute a perna para trás, contraindo o glúteo no topo.",
                "IpT5NjpVHqQ"));

        // ── LEGS: Calves ──────────────────────────────────────────────────────
        ex.add(new Exercise("calf-raise", "Standing Calf Raise", "Elevação de Panturrilha em Pé",
                Set.of("CALVES"), Set.of(),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Stand on edge of step. Rise onto toes as high as possible, lower below step level.",
                "Fique na borda de um degrau. Suba nas pontas dos pés o mais alto possível, abaixe abaixo do nível do degrau.",
                "gwLzBJRsigM"));

        ex.add(new Exercise("seated-calf-raise", "Seated Calf Raise", "Elevação de Panturrilha Sentado",
                Set.of("CALVES"), Set.of(),
                Set.of("MACHINE"), "BEGINNER",
                "Sit in machine with knees under pad. Rise up on toes, hold, then lower slowly.",
                "Sente com os joelhos sob o suporte. Suba nas pontas dos pés, segure e abaixe lentamente.",
                "JbyjNymZOt0"));

        // ── PULL: Traps ───────────────────────────────────────────────────────
        ex.add(new Exercise("barbell-shrug", "Barbell Shrug", "Encolhimento com Barra",
                Set.of("TRAPS"), Set.of("SHOULDERS"),
                Set.of("BARBELL"), "BEGINNER",
                "Hold bar at hips. Shrug shoulders straight up as high as possible, hold, then lower.",
                "Segure a barra nos quadris. Encolha os ombros o mais alto possível, segure e abaixe.",
                "g6qbq4Lf1FI"));

        ex.add(new Exercise("dumbbell-shrug", "Dumbbell Shrug", "Encolhimento com Halteres",
                Set.of("TRAPS"), Set.of("SHOULDERS"),
                Set.of("DUMBBELLS"), "BEGINNER",
                "Hold dumbbells at sides. Shrug shoulders toward ears, hold briefly, then lower.",
                "Segure halteres ao lado do corpo. Encolha os ombros em direção às orelhas, segure brevemente e abaixe.",
                "cXbSJHtjrQQ"));

        // ── CORE: Abs ─────────────────────────────────────────────────────────
        ex.add(new Exercise("plank", "Plank", "Prancha",
                Set.of("ABS"), Set.of("OBLIQUES"),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Hold a push-up position on forearms. Keep body straight from head to heels. Breathe normally.",
                "Segure a posição de flexão nos antebraços. Mantenha o corpo reto da cabeça aos calcanhares. Respire normalmente.",
                "ASdvN_XEl_c"));

        ex.add(new Exercise("crunch", "Crunch", "Abdominal",
                Set.of("ABS"), Set.of(),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Lie on back, knees bent. Curl shoulders off floor toward knees, then lower. Keep lower back pressed down.",
                "Deite com joelhos dobrados. Curve os ombros do chão em direção aos joelhos, depois abaixe. Mantenha a lombar pressionada.",
                "Xyd_fa5zoEU"));

        ex.add(new Exercise("leg-raise", "Hanging Leg Raise", "Elevação de Pernas Suspenso",
                Set.of("ABS"), Set.of("OBLIQUES"),
                Set.of("PULL_UP_BAR"), "INTERMEDIATE",
                "Hang from bar. Raise legs until parallel to floor (or higher). Lower slowly without swinging.",
                "Pendure-se na barra. Levante as pernas até ficarem paralelas ao chão (ou mais alto). Abaixe lentamente sem balançar.",
                "JB2oyawG9KI"));

        ex.add(new Exercise("russian-twist", "Russian Twist", "Torção Russa",
                Set.of("OBLIQUES"), Set.of("ABS"),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Sit with knees bent, lean back slightly. Rotate torso left and right, touching hands to floor each side.",
                "Sente com joelhos dobrados, incline levemente. Gire o torso para a esquerda e direita, tocando as mãos no chão em cada lado.",
                "wkD8rjkodUI"));

        ex.add(new Exercise("ab-wheel-rollout", "Ab Wheel Rollout", "Roda Abdominal",
                Set.of("ABS"), Set.of("OBLIQUES"),
                Set.of("BODYWEIGHT"), "ADVANCED",
                "Kneel holding ab wheel. Roll forward until nearly horizontal, then pull back with abs.",
                "Ajoelhe segurando a roda. Role para frente até quase horizontal, depois puxe de volta com o abdômen.",
                "7y1GiNDv5zg"));

        ex.add(new Exercise("mountain-climber", "Mountain Climber", "Escalador",
                Set.of("ABS"), Set.of("OBLIQUES"),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "High plank position. Drive knees alternately toward chest in a running motion.",
                "Posição de prancha alta. Leve os joelhos alternadamente em direção ao peito em movimento de corrida.",
                "nmwgirgXLYM"));

        ex.add(new Exercise("cable-crunch", "Cable Crunch", "Abdominal no Cabo",
                Set.of("ABS"), Set.of(),
                Set.of("CABLES"), "BEGINNER",
                "Kneel below a high cable pulley holding rope. Curl torso down toward knees, contracting abs.",
                "Ajoelhe abaixo da polia alta segurando a corda. Curve o torso em direção aos joelhos, contraindo o abdômen.",
                "QDc1Rd6VlFM"));

        // ── FULL BODY ─────────────────────────────────────────────────────────
        ex.add(new Exercise("burpee", "Burpee", "Burpee",
                Set.of("QUADS"), Set.of("CHEST", "SHOULDERS", "ABS"),
                Set.of("BODYWEIGHT"), "INTERMEDIATE",
                "From standing, drop to push-up, perform push-up, jump feet to hands, jump up with arms overhead.",
                "De pé, caia para a posição de flexão, faça a flexão, salte os pés até as mãos e salte para cima com os braços acima.",
                "qLBImHhCXSw"));

        ex.add(new Exercise("clean-and-press", "Clean and Press", "Arremesso",
                Set.of("SHOULDERS"), Set.of("QUADS", "BACK", "TRAPS"),
                Set.of("BARBELL"), "ADVANCED",
                "Deadlift bar, then explosively pull it to shoulders (clean). Press overhead. Lower back to start.",
                "Levante a barra, depois puxe explosivamente até os ombros (arranco). Pressione acima da cabeça. Abaixe de volta.",
                "ZBOKByJ1cCQ"));

        ex.add(new Exercise("turkish-get-up", "Turkish Get-Up", "Levantamento Turco",
                Set.of("SHOULDERS"), Set.of("ABS", "GLUTES", "QUADS"),
                Set.of("DUMBBELLS"), "ADVANCED",
                "Lie holding weight overhead. Follow the full sequence to stand up while keeping weight locked out overhead.",
                "Deite segurando o peso acima da cabeça. Siga a sequência completa para se levantar mantendo o peso acima.",
                "nL7CJDCoqxE"));

        ex.add(new Exercise("kettlebell-swing", "Kettlebell Swing", "Swing com Kettlebell",
                Set.of("GLUTES"), Set.of("HAMSTRINGS", "BACK", "SHOULDERS"),
                Set.of("DUMBBELLS"), "INTERMEDIATE",
                "Hinge at hips to swing the weight between legs then drive hips forward to swing it to shoulder height.",
                "Incline-se nos quadris para balançar o peso entre as pernas e depois empurre os quadris para frente até a altura dos ombros.",
                "YSxHifyI6s8"));

        // ── ISOLATION: Forearms & Additional ─────────────────────────────────
        ex.add(new Exercise("wrist-curl", "Wrist Curl", "Rosca de Pulso",
                Set.of("FOREARMS"), Set.of(),
                Set.of("BARBELL"), "BEGINNER",
                "Sit with forearms on thighs, palms up. Curl wrists up, lower below neutral.",
                "Sente com antebraços nas coxas, palmas para cima. Curve os pulsos para cima e abaixe abaixo do neutro.",
                "GkjIEPxKnXc"));

        ex.add(new Exercise("reverse-curl", "Reverse Curl", "Rosca Inversa",
                Set.of("FOREARMS"), Set.of("BICEPS"),
                Set.of("BARBELL"), "BEGINNER",
                "Hold bar with overhand grip. Curl up keeping wrists neutral, lower slowly.",
                "Segure a barra em pegada pronada. Faça a rosca mantendo os pulsos neutros, abaixe lentamente.",
                "cS5pLJzNGCI"));

        ex.add(new Exercise("incline-dumbbell-curl", "Incline Dumbbell Curl", "Rosca Inclinada com Halteres",
                Set.of("BICEPS"), Set.of("FOREARMS"),
                Set.of("DUMBBELLS", "BENCH"), "INTERMEDIATE",
                "Sit on incline bench. Let arms hang with dumbbells. Curl up with a full stretch at the bottom.",
                "Sente no banco inclinado. Deixe os braços pendurados com halteres. Faça a rosca com alongamento completo na posição baixa.",
                "X4Zjuq7BqgM"));

        ex.add(new Exercise("concentration-curl", "Concentration Curl", "Rosca Concentrada",
                Set.of("BICEPS"), Set.of(),
                Set.of("DUMBBELLS"), "BEGINNER",
                "Sit and brace elbow against inner thigh. Curl dumbbell up to shoulder, focusing on peak contraction.",
                "Sente e apoie o cotovelo na coxa interna. Faça a rosca até o ombro focando na contração máxima.",
                "Jd2wQRMIYKs"));

        ex.add(new Exercise("overhead-tricep-extension", "Overhead Tricep Extension", "Tríceps Francês",
                Set.of("TRICEPS"), Set.of(),
                Set.of("DUMBBELLS"), "BEGINNER",
                "Hold one dumbbell overhead with both hands. Lower behind head by bending elbows, then extend.",
                "Segure um halter acima com ambas as mãos. Abaixe atrás da cabeça dobrando os cotovelos, depois estenda.",
                "YbX7Wd8jQ-Q"));

        ex.add(new Exercise("close-grip-bench-press", "Close-Grip Bench Press", "Supino Fechado",
                Set.of("TRICEPS"), Set.of("CHEST"),
                Set.of("BARBELL", "BENCH"), "INTERMEDIATE",
                "Grip bar with hands shoulder-width or narrower. Lower to chest and press up focusing on tricep engagement.",
                "Pegue a barra com as mãos na largura dos ombros ou mais fechado. Abaixe ao peito e pressione focando nos tríceps.",
                "nRiJVZDpdL0"));

        // ── NECK ─────────────────────────────────────────────────────────────
        ex.add(new Exercise("neck-flexion", "Neck Flexion", "Flexão de Pescoço",
                Set.of("NECK"), Set.of(),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Seated, slowly bring chin to chest, then return to neutral. Controlled movement only.",
                "Sentado, leve o queixo lentamente ao peito, depois retorne ao neutro. Apenas movimentos controlados.",
                null));

        // ── SHOULDERS: Rear Delt ──────────────────────────────────────────────
        ex.add(new Exercise("rear-delt-fly", "Rear Delt Fly", "Crucifixo Invertido",
                Set.of("SHOULDERS"), Set.of("BACK", "TRAPS"),
                Set.of("DUMBBELLS"), "BEGINNER",
                "Hinge forward to nearly horizontal. Raise arms out to sides, focusing on rear delts and squeezing at top.",
                "Incline-se até quase horizontal. Levante os braços para os lados focando nos deltoides posteriores.",
                "EA7u4Q_8HQ0"));

        ex.add(new Exercise("cable-lateral-raise", "Cable Lateral Raise", "Elevação Lateral no Cabo",
                Set.of("SHOULDERS"), Set.of(),
                Set.of("CABLES"), "BEGINNER",
                "Stand beside low cable. Raise arm across body and up to shoulder height in an arc.",
                "Fique ao lado da polia baixa. Levante o braço cruzando o corpo até a altura do ombro em arco.",
                "PPunUnGBFKQ"));

        // ── BACK: Lower ───────────────────────────────────────────────────────
        ex.add(new Exercise("hyperextension", "Back Extension", "Hiperextensão",
                Set.of("BACK"), Set.of("GLUTES", "HAMSTRINGS"),
                Set.of("MACHINE"), "BEGINNER",
                "Lie face down on hyperextension bench. Lower torso then raise back to parallel using lower back and glutes.",
                "Deite de bruços no banco de hiperextensão. Abaixe o torso e levante de volta ao paralelo usando a lombar e os glúteos.",
                "ph3pddpKzzw"));

        ex.add(new Exercise("good-morning", "Good Morning", "Bom Dia",
                Set.of("HAMSTRINGS"), Set.of("BACK", "GLUTES"),
                Set.of("BARBELL"), "INTERMEDIATE",
                "Bar on upper back. Hinge at hips with soft knees until torso is nearly parallel, then return.",
                "Barra nas costas superiores. Incline-se nos quadris com joelhos levemente dobrados até o torso ficar quase paralelo, depois retorne.",
                "4M_pCCZJuUw"));

        ex.add(new Exercise("superman", "Superman", "Superman",
                Set.of("BACK"), Set.of("GLUTES", "HAMSTRINGS"),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Lie face down. Simultaneously raise arms and legs off the floor, hold briefly, then lower.",
                "Deite de bruços. Levante braços e pernas do chão simultaneamente, segure brevemente e abaixe.",
                "cc3pTkMGNNE"));

        // ── PUSH: Chest variations ────────────────────────────────────────────
        ex.add(new Exercise("decline-bench-press", "Decline Bench Press", "Supino Declinado",
                Set.of("CHEST"), Set.of("TRICEPS"),
                Set.of("BARBELL", "BENCH"), "INTERMEDIATE",
                "Set bench to decline. Lower bar to lower chest, press back up focusing on lower pec contraction.",
                "Ajuste o banco declinado. Abaixe a barra até o peito inferior e pressione de volta focando na contração do peitoral inferior.",
                "LfyQTbBtmcA"));

        ex.add(new Exercise("pec-deck", "Pec Deck Fly", "Peck Deck",
                Set.of("CHEST"), Set.of("SHOULDERS"),
                Set.of("MACHINE"), "BEGINNER",
                "Sit at machine, elbows on pads. Bring arms together in front, squeeze chest, then open slowly.",
                "Sente na máquina, cotovelos nos suportes. Junte os braços na frente, contraia o peito e abra lentamente.",
                "4x4SFvMEPk4"));

        ex.add(new Exercise("push-up-wide", "Wide Push-Up", "Flexão Aberta",
                Set.of("CHEST"), Set.of("SHOULDERS"),
                Set.of("BODYWEIGHT"), "BEGINNER",
                "Push-up with hands wider than shoulder-width to emphasize chest muscles.",
                "Flexão com as mãos mais largas que a largura dos ombros para enfatizar os músculos do peito.",
                "fGHyU3fBpYQ"));

        ex.add(new Exercise("diamond-push-up", "Diamond Push-Up", "Flexão Diamante",
                Set.of("TRICEPS"), Set.of("CHEST"),
                Set.of("BODYWEIGHT"), "INTERMEDIATE",
                "Form a diamond shape with hands under chest. Push up, keeping elbows close to body.",
                "Forme um diamante com as mãos sob o peito. Empurre para cima, mantendo os cotovelos próximos ao corpo.",
                "J0DXBSpgBxY"));

        ex.add(new Exercise("resistance-band-chest-press", "Band Chest Press", "Supino com Elástico",
                Set.of("CHEST"), Set.of("TRICEPS", "SHOULDERS"),
                Set.of("RESISTANCE_BANDS"), "BEGINNER",
                "Anchor band behind you at chest height. Press forward extending arms, then return with control.",
                "Fixe o elástico atrás na altura do peito. Pressione para frente estendendo os braços, depois retorne com controle.",
                null));

        ex.add(new Exercise("resistance-band-pull-apart", "Band Pull Apart", "Abertura com Elástico",
                Set.of("BACK"), Set.of("SHOULDERS", "TRAPS"),
                Set.of("RESISTANCE_BANDS"), "BEGINNER",
                "Hold band at shoulder height in front. Pull it apart by moving arms out to your sides.",
                "Segure o elástico na altura dos ombros à frente. Abra-o movendo os braços para os lados.",
                null));

        ex.add(new Exercise("resistance-band-squat", "Band Squat", "Agachamento com Elástico",
                Set.of("QUADS"), Set.of("GLUTES", "HAMSTRINGS"),
                Set.of("RESISTANCE_BANDS"), "BEGINNER",
                "Stand on band, hold handles at shoulders. Squat down to parallel, drive up through heels.",
                "Fique sobre o elástico, segure as alças nos ombros. Agache até o paralelo e empurre pelos calcanhares.",
                null));

        return ex;
    }
}
