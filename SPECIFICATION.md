# Daily Trivia Challenge - Technical Specification & Implementation Plan

## 📋 Overview

**Quizzy** is a daily trivia challenge mobile application for Android that engages users with a new trivia question each day. The app tracks user performance through streaks, rankings, and achievement badges, creating a gamified learning experience.

### Technology Stack
- **Platform**: Android (API 24+)
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Repository Pattern + Clean Architecture
- **Local Storage**: Room Database + DataStore (Preferences)
- **Dependency Injection**: Hilt
- **Coroutines**: Kotlin Coroutines + Flow
- **Remote Data**: Firebase Firestore (primary) or Mock JSON Server (development)

---

## 🎨 Screens & User Flow

### Screen List

1. **Splash Screen**
   - App logo and branding
   - Initial data loading
   - Navigation to Home or Onboarding

2. **Home Screen**
   - Daily question preview
   - Current streak status
   - Quick stats (total score, rank)
   - Navigate to question, leaderboard, or profile

3. **Trivia Question Screen**
   - Question text
   - 4 multiple-choice answers
   - Optional countdown timer (30 seconds)
   - Submit button
   - Progress indicator

4. **Results Screen**
   - Correct/Incorrect feedback
   - Correct answer display
   - Score gained/lost
   - Streak update
   - New badge unlock animation (if applicable)
   - Continue button

5. **Leaderboard Screen**
   - Tabs: Weekly / All-Time
   - User rankings with scores
   - Current user highlight
   - Refresh functionality

6. **Profile Screen**
   - Username and avatar
   - Total score
   - Current streak & best streak
   - Badges earned count
   - Navigate to Badge Gallery

7. **Badge Gallery Screen**
   - Grid layout of all badges
   - Locked vs Unlocked states
   - Badge details on tap
   - Progress bars for incremental badges

### UX Flow

```
Splash → Home → Trivia Question → Results → [Badge Unlock Animation] → Home
          ↓
          ├→ Leaderboard
          ├→ Profile → Badge Gallery
          └→ Settings
```

---

## 🏗️ Architecture

### Clean Architecture Layers

```
┌─────────────────────────────────────┐
│         Presentation Layer          │
│  (Compose UI + ViewModels)         │
└─────────────────────────────────────┘
              ↓ ↑
┌─────────────────────────────────────┐
│         Domain Layer                │
│  (Use Cases + Models + Repositories)│
└─────────────────────────────────────┘
              ↓ ↑
┌─────────────────────────────────────┐
│         Data Layer                  │
│  (Room + DataStore + Remote API)   │
└─────────────────────────────────────┘
```

### Project Structure

```
app/
├── src/main/java/com/example/quizzy/
│   ├── QuizzyApplication.kt
│   ├── di/                          # Dependency Injection
│   │   ├── AppModule.kt
│   │   ├── DatabaseModule.kt
│   │   └── RepositoryModule.kt
│   │
│   ├── data/                        # Data Layer
│   │   ├── local/
│   │   │   ├── database/
│   │   │   │   ├── QuizzyDatabase.kt
│   │   │   │   ├── dao/
│   │   │   │   │   ├── UserDao.kt
│   │   │   │   │   ├── TriviaQuestionDao.kt
│   │   │   │   │   ├── ResultDao.kt
│   │   │   │   │   ├── BadgeDao.kt
│   │   │   │   │   └── LeaderboardDao.kt
│   │   │   │   └── entity/
│   │   │   │       ├── UserEntity.kt
│   │   │   │       ├── TriviaQuestionEntity.kt
│   │   │   │       ├── ResultEntity.kt
│   │   │   │       ├── BadgeEntity.kt
│   │   │   │       └── LeaderboardEntryEntity.kt
│   │   │   └── datastore/
│   │   │       └── PreferencesManager.kt
│   │   ├── remote/
│   │   │   ├── api/
│   │   │   │   └── TriviaApiService.kt
│   │   │   └── dto/
│   │   │       └── TriviaQuestionDto.kt
│   │   └── repository/
│   │       ├── UserRepositoryImpl.kt
│   │       ├── TriviaRepositoryImpl.kt
│   │       ├── BadgeRepositoryImpl.kt
│   │       └── LeaderboardRepositoryImpl.kt
│   │
│   ├── domain/                      # Domain Layer
│   │   ├── model/
│   │   │   ├── User.kt
│   │   │   ├── TriviaQuestion.kt
│   │   │   ├── Answer.kt
│   │   │   ├── Result.kt
│   │   │   ├── Badge.kt
│   │   │   ├── BadgeType.kt
│   │   │   └── LeaderboardEntry.kt
│   │   ├── repository/
│   │   │   ├── UserRepository.kt
│   │   │   ├── TriviaRepository.kt
│   │   │   ├── BadgeRepository.kt
│   │   │   └── LeaderboardRepository.kt
│   │   └── usecase/
│   │       ├── GetDailyQuestionUseCase.kt
│   │       ├── SubmitAnswerUseCase.kt
│   │       ├── UpdateStreakUseCase.kt
│   │       ├── CheckBadgeUnlocksUseCase.kt
│   │       ├── GetLeaderboardUseCase.kt
│   │       └── GetUserProfileUseCase.kt
│   │
│   └── presentation/                # Presentation Layer
│       ├── theme/
│       │   ├── Color.kt
│       │   ├── Theme.kt
│       │   └── Type.kt
│       ├── component/
│       │   ├── QuizButton.kt
│       │   ├── AnswerCard.kt
│       │   ├── BadgeCard.kt
│       │   ├── LeaderboardItem.kt
│       │   ├── StreakIndicator.kt
│       │   └── TimerProgress.kt
│       ├── navigation/
│       │   ├── NavGraph.kt
│       │   └── Screen.kt
│       └── screen/
│           ├── splash/
│           │   ├── SplashScreen.kt
│           │   └── SplashViewModel.kt
│           ├── home/
│           │   ├── HomeScreen.kt
│           │   └── HomeViewModel.kt
│           ├── trivia/
│           │   ├── TriviaScreen.kt
│           │   └── TriviaViewModel.kt
│           ├── result/
│           │   ├── ResultScreen.kt
│           │   └── ResultViewModel.kt
│           ├── leaderboard/
│           │   ├── LeaderboardScreen.kt
│           │   └── LeaderboardViewModel.kt
│           ├── profile/
│           │   ├── ProfileScreen.kt
│           │   └── ProfileViewModel.kt
│           └── badges/
│               ├── BadgeGalleryScreen.kt
│               └── BadgeGalleryViewModel.kt
```

---

## 📊 Data Models

### Domain Models

```kotlin
// User.kt
data class User(
    val id: String,
    val username: String,
    val totalScore: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val badgesEarned: List<String> = emptyList(),
    val lastPlayedDate: LocalDate? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

// TriviaQuestion.kt
data class TriviaQuestion(
    val id: String,
    val question: String,
    val answers: List<Answer>,
    val correctAnswerIndex: Int,
    val category: String,
    val difficulty: Difficulty,
    val date: LocalDate,
    val timeLimit: Int = 30 // seconds
)

enum class Difficulty {
    EASY, MEDIUM, HARD
}

// Answer.kt
data class Answer(
    val id: String,
    val text: String
)

// Result.kt
data class Result(
    val id: String,
    val userId: String,
    val questionId: String,
    val selectedAnswerIndex: Int,
    val isCorrect: Boolean,
    val pointsEarned: Int,
    val timeSpent: Int, // seconds
    val answeredAt: LocalDateTime
)

// Badge.kt
data class Badge(
    val id: String,
    val name: String,
    val description: String,
    val iconRes: Int,
    val type: BadgeType,
    val unlockCondition: UnlockCondition,
    val isUnlocked: Boolean = false,
    val progress: Int = 0,
    val unlockedAt: LocalDateTime? = null
)

enum class BadgeType {
    PARTICIPATION,
    STREAK,
    SCORE_MILESTONE,
    SPEED,
    PERFECTION,
    SPECIAL
}

sealed class UnlockCondition {
    data class AnswerCount(val count: Int) : UnlockCondition()
    data class StreakCount(val count: Int) : UnlockCondition()
    data class TotalScore(val score: Int) : UnlockCondition()
    data class SpeedAnswer(val seconds: Int) : UnlockCondition()
    data class PerfectWeek(val weeks: Int) : UnlockCondition()
}

// LeaderboardEntry.kt
data class LeaderboardEntry(
    val rank: Int,
    val userId: String,
    val username: String,
    val score: Int,
    val isCurrentUser: Boolean = false
)
```

---

## 🎮 Business Rules & Game Logic

### Scoring System

```kotlin
fun calculateScore(
    isCorrect: Boolean,
    difficulty: Difficulty,
    timeSpent: Int,
    timeLimit: Int
): Int {
    if (!isCorrect) return 0
    
    val basePoints = when (difficulty) {
        Difficulty.EASY -> 10
        Difficulty.MEDIUM -> 20
        Difficulty.HARD -> 30
    }
    
    // Speed bonus: up to 50% extra points
    val timeRatio = timeSpent.toFloat() / timeLimit
    val speedMultiplier = when {
        timeRatio <= 0.3f -> 1.5f  // Answered in 30% of time
        timeRatio <= 0.5f -> 1.3f  // Answered in 50% of time
        timeRatio <= 0.7f -> 1.1f  // Answered in 70% of time
        else -> 1.0f
    }
    
    return (basePoints * speedMultiplier).toInt()
}
```

### Streak Calculation

```kotlin
fun updateStreak(
    currentStreak: Int,
    bestStreak: Int,
    lastPlayedDate: LocalDate?,
    isCorrect: Boolean,
    today: LocalDate = LocalDate.now()
): Pair<Int, Int> {
    if (!isCorrect) {
        return 0 to bestStreak // Streak broken
    }
    
    val newStreak = when {
        lastPlayedDate == null -> 1 // First time playing
        lastPlayedDate == today.minusDays(1) -> currentStreak + 1 // Consecutive day
        lastPlayedDate == today -> currentStreak // Already played today
        else -> 1 // Gap in playing, start new streak
    }
    
    val newBestStreak = maxOf(bestStreak, newStreak)
    return newStreak to newBestStreak
}
```

### Daily Question Reset

```kotlin
fun isDailyQuestionAvailable(lastPlayedDate: LocalDate?, today: LocalDate = LocalDate.now()): Boolean {
    return lastPlayedDate == null || lastPlayedDate < today
}
```

### Leaderboard Sorting

- **Weekly Leaderboard**: Sum of scores from the last 7 days, sorted descending
- **All-Time Leaderboard**: Total score, sorted descending
- Tie-breaker: Best streak (higher is better), then earliest join date

---

## 🏆 Achievement System

### Badge Categories & Examples

#### 1. Participation Badges
| Badge Name | Description | Unlock Condition |
|------------|-------------|------------------|
| First Steps | Answer your first question | Answer 1 question |
| Getting Started | Answer 5 questions | Answer 5 questions |
| Dedicated | Answer 30 questions | Answer 30 questions |
| Trivia Master | Answer 100 questions | Answer 100 questions |

#### 2. Streak Badges
| Badge Name | Description | Unlock Condition |
|------------|-------------|------------------|
| On Fire | 3-day streak | 3 consecutive correct days |
| Burning Bright | 7-day streak | 7 consecutive correct days |
| Unstoppable | 14-day streak | 14 consecutive correct days |
| Legendary | 30-day streak | 30 consecutive correct days |

#### 3. Score Milestone Badges
| Badge Name | Description | Unlock Condition |
|------------|-------------|------------------|
| Bronze Scorer | Earn 100 points | Total score ≥ 100 |
| Silver Scorer | Earn 500 points | Total score ≥ 500 |
| Gold Scorer | Earn 1000 points | Total score ≥ 1000 |
| Diamond Scorer | Earn 5000 points | Total score ≥ 5000 |

#### 4. Speed Badges
| Badge Name | Description | Unlock Condition |
|------------|-------------|------------------|
| Quick Thinker | Answer in under 10 seconds | Answer correctly in ≤ 10s |
| Lightning Fast | Answer 5 questions in under 5 seconds | 5 answers in ≤ 5s each |
| Speed Demon | Average answer time under 8 seconds | Avg time ≤ 8s over 10 questions |

#### 5. Perfection Badges
| Badge Name | Description | Unlock Condition |
|------------|-------------|------------------|
| Perfect Week | 7 correct answers in a row | 7 consecutive correct |
| Perfect Month | 30 correct answers in 30 days | 30/30 correct in calendar month |
| Flawless | First 10 answers all correct | 10/10 on first 10 questions |

### Badge Unlock Logic

```kotlin
suspend fun checkAndUnlockBadges(userId: String, result: Result): List<Badge> {
    val user = userRepository.getUser(userId)
    val allBadges = badgeRepository.getAllBadges()
    val userResults = resultRepository.getUserResults(userId)
    val newlyUnlockedBadges = mutableListOf<Badge>()
    
    allBadges.filter { !it.isUnlocked }.forEach { badge ->
        val shouldUnlock = when (badge.unlockCondition) {
            is UnlockCondition.AnswerCount -> {
                userResults.size >= badge.unlockCondition.count
            }
            is UnlockCondition.StreakCount -> {
                user.currentStreak >= badge.unlockCondition.count
            }
            is UnlockCondition.TotalScore -> {
                user.totalScore >= badge.unlockCondition.score
            }
            is UnlockCondition.SpeedAnswer -> {
                result.isCorrect && result.timeSpent <= badge.unlockCondition.seconds
            }
            is UnlockCondition.PerfectWeek -> {
                val lastWeekResults = userResults.filter { 
                    it.answeredAt.isAfter(LocalDateTime.now().minusWeeks(1))
                }
                lastWeekResults.size == 7 && lastWeekResults.all { it.isCorrect }
            }
        }
        
        if (shouldUnlock) {
            val unlockedBadge = badge.copy(
                isUnlocked = true,
                unlockedAt = LocalDateTime.now()
            )
            badgeRepository.unlockBadge(userId, unlockedBadge)
            newlyUnlockedBadges.add(unlockedBadge)
        }
    }
    
    return newlyUnlockedBadges
}
```

---

## 💾 Data Persistence

### Room Database

```kotlin
@Database(
    entities = [
        UserEntity::class,
        TriviaQuestionEntity::class,
        ResultEntity::class,
        BadgeEntity::class,
        UserBadgeCrossRef::class,
        LeaderboardEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class QuizzyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun triviaQuestionDao(): TriviaQuestionDao
    abstract fun resultDao(): ResultDao
    abstract fun badgeDao(): BadgeDao
    abstract fun leaderboardDao(): LeaderboardDao
}

// UserDao.kt
@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserFlow(userId: String): Flow<UserEntity?>
    
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUser(userId: String): UserEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    @Query("UPDATE users SET totalScore = :score WHERE id = :userId")
    suspend fun updateScore(userId: String, score: Int)
    
    @Query("UPDATE users SET currentStreak = :streak, bestStreak = :bestStreak WHERE id = :userId")
    suspend fun updateStreak(userId: String, streak: Int, bestStreak: Int)
}
```

### DataStore (Preferences)

```kotlin
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "quizzy_prefs")
    
    companion object {
        val CURRENT_USER_ID = stringPreferencesKey("current_user_id")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    }
    
    val preferencesFlow: Flow<Preferences> = context.dataStore.data
    
    suspend fun setCurrentUserId(userId: String) {
        context.dataStore.edit { prefs ->
            prefs[CURRENT_USER_ID] = userId
        }
    }
    
    fun getCurrentUserId(): Flow<String?> = preferencesFlow.map { prefs ->
        prefs[CURRENT_USER_ID]
    }
}
```

---

## 🌐 Backend Strategy

### Option 1: Firebase Firestore (Recommended)

**Pros:**
- Real-time leaderboard updates
- User authentication
- Cloud storage for questions
- Free tier sufficient for MVP
- Easy integration with Android

**Structure:**
```
collections/
├── users/
│   └── {userId}/
│       ├── username: String
│       ├── totalScore: Number
│       ├── currentStreak: Number
│       ├── bestStreak: Number
│       ├── badges: Array<String>
│       └── lastPlayedDate: Timestamp
│
├── questions/
│   └── {questionId}/
│       ├── question: String
│       ├── answers: Array<String>
│       ├── correctAnswerIndex: Number
│       ├── category: String
│       ├── difficulty: String
│       └── date: Timestamp
│
├── results/
│   └── {resultId}/
│       ├── userId: String
│       ├── questionId: String
│       ├── isCorrect: Boolean
│       ├── pointsEarned: Number
│       ├── timeSpent: Number
│       └── answeredAt: Timestamp
│
└── leaderboard/
    ├── weekly/
    │   └── {userId}/
    └── allTime/
        └── {userId}/
```

**Implementation:**
```kotlin
dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
}
```

### Option 2: Mock JSON Server (Development)

For offline development and testing:

```kotlin
// MockTriviaService.kt
class MockTriviaService {
    private val questions = listOf(
        TriviaQuestion(
            id = "q1",
            question = "What is the capital of France?",
            answers = listOf(
                Answer("a1", "London"),
                Answer("a2", "Paris"),
                Answer("a3", "Berlin"),
                Answer("a4", "Madrid")
            ),
            correctAnswerIndex = 1,
            category = "Geography",
            difficulty = Difficulty.EASY,
            date = LocalDate.now()
        ),
        // More questions...
    )
    
    suspend fun getDailyQuestion(date: LocalDate): TriviaQuestion? {
        delay(500) // Simulate network delay
        return questions.find { it.date == date }
    }
}
```

### Option 3: Hybrid Approach (Recommended for Production)

- **Local-first**: All data stored in Room for offline access
- **Sync**: Periodically sync with Firebase when online
- **Leaderboard**: Fetch from Firebase, cache in Room
- **Questions**: Pre-populate Room with questions, update from Firebase

---

## 🎨 UI/UX Implementation

### Theme Configuration

```kotlin
// Color.kt
val PrimaryLight = Color(0xFF6200EE)
val PrimaryVariantLight = Color(0xFF3700B3)
val SecondaryLight = Color(0xFF03DAC6)
val BackgroundLight = Color(0xFFFAFAFA)
val SurfaceLight = Color(0xFFFFFFFF)
val CorrectGreen = Color(0xFF4CAF50)
val IncorrectRed = Color(0xFFF44336)
val GoldBadge = Color(0xFFFFD700)

val PrimaryDark = Color(0xFFBB86FC)
val PrimaryVariantDark = Color(0xFF3700B3)
val SecondaryDark = Color(0xFF03DAC6)
val BackgroundDark = Color(0xFF121212)
val SurfaceDark = Color(0xFF1E1E1E)

// Theme.kt
@Composable
fun QuizzyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = PrimaryDark,
            secondary = SecondaryDark,
            background = BackgroundDark,
            surface = SurfaceDark
        )
    } else {
        lightColorScheme(
            primary = PrimaryLight,
            secondary = SecondaryLight,
            background = BackgroundLight,
            surface = SurfaceLight
        )
    }
    
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
```

### Sample Compose Screens

#### Home Screen

```kotlin
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToTrivia: () -> Unit,
    onNavigateToLeaderboard: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quizzy") },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.Person, "Profile")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Streak Card
            StreakCard(
                currentStreak = uiState.user?.currentStreak ?: 0,
                bestStreak = uiState.user?.bestStreak ?: 0
            )
            
            // Daily Question Card
            if (uiState.isDailyQuestionAvailable) {
                DailyQuestionCard(
                    category = uiState.dailyQuestion?.category ?: "",
                    difficulty = uiState.dailyQuestion?.difficulty ?: Difficulty.EASY,
                    onClick = onNavigateToTrivia
                )
            } else {
                AlreadyPlayedCard()
            }
            
            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatCard(
                    label = "Score",
                    value = uiState.user?.totalScore?.toString() ?: "0"
                )
                StatCard(
                    label = "Badges",
                    value = uiState.user?.badgesEarned?.size?.toString() ?: "0"
                )
            }
            
            // Leaderboard Button
            Button(
                onClick = onNavigateToLeaderboard,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Leaderboard")
            }
        }
    }
}

@Composable
fun StreakCard(currentStreak: Int, bestStreak: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = if (currentStreak > 0) Color(0xFFFF6B00) else Color.Gray,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "$currentStreak",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text("Current Streak", style = MaterialTheme.typography.bodySmall)
            }
            
            VerticalDivider()
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = GoldBadge,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "$bestStreak",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text("Best Streak", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
```

#### Trivia Question Screen

```kotlin
@Composable
fun TriviaScreen(
    viewModel: TriviaViewModel = hiltViewModel(),
    onNavigateToResult: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val question = uiState.question ?: return
    
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Timer
            if (uiState.timeRemaining != null) {
                TimerProgress(
                    timeRemaining = uiState.timeRemaining!!,
                    totalTime = question.timeLimit
                )
            }
            
            // Question
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = question.category,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = question.question,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            
            // Answers
            question.answers.forEachIndexed { index, answer ->
                AnswerCard(
                    answer = answer.text,
                    isSelected = uiState.selectedAnswerIndex == index,
                    onClick = { viewModel.selectAnswer(index) },
                    enabled = uiState.selectedAnswerIndex == null
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Submit Button
            Button(
                onClick = {
                    viewModel.submitAnswer()
                    onNavigateToResult()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.selectedAnswerIndex != null
            ) {
                Text("Submit Answer")
            }
        }
    }
}

@Composable
fun AnswerCard(
    answer: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isSelected) {
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = null,
                enabled = enabled
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = answer)
        }
    }
}

@Composable
fun TimerProgress(timeRemaining: Int, totalTime: Int) {
    Column {
        LinearProgressIndicator(
            progress = { timeRemaining.toFloat() / totalTime },
            modifier = Modifier.fillMaxWidth(),
            color = when {
                timeRemaining > totalTime * 0.5 -> Color.Green
                timeRemaining > totalTime * 0.25 -> Color.Yellow
                else -> Color.Red
            }
        )
        Text(
            text = "${timeRemaining}s remaining",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.End)
        )
    }
}
```

#### Result Screen with Badge Unlock Animation

```kotlin
@Composable
fun ResultScreen(
    viewModel: ResultViewModel = hiltViewModel(),
    onContinue: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val result = uiState.result ?: return
    
    LaunchedEffect(Unit) {
        viewModel.checkBadgeUnlocks()
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Result Icon
            Icon(
                imageVector = if (result.isCorrect) {
                    Icons.Default.CheckCircle
                } else {
                    Icons.Default.Cancel
                },
                contentDescription = null,
                tint = if (result.isCorrect) CorrectGreen else IncorrectRed,
                modifier = Modifier.size(120.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Result Text
            Text(
                text = if (result.isCorrect) "Correct!" else "Incorrect",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Points Earned
            if (result.isCorrect) {
                Text(
                    text = "+${result.pointsEarned} points",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Text(
                    text = "The correct answer was: ${uiState.correctAnswer}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Streak Update
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Streak Status", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (result.isCorrect) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = Color(0xFFFF6B00)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "${uiState.newStreak} days",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        Text(
                            "Streak broken",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Continue Button
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue")
            }
        }
        
        // Badge Unlock Overlay
        uiState.newlyUnlockedBadges.forEach { badge ->
            BadgeUnlockAnimation(
                badge = badge,
                onDismiss = { viewModel.dismissBadge(badge.id) }
            )
        }
    }
}

@Composable
fun BadgeUnlockAnimation(badge: Badge, onDismiss: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
        delay(3000)
        onDismiss()
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f))
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(32.dp)
                    .animateContentSize()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(badge.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = GoldBadge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Badge Unlocked!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        badge.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        badge.description,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
```

### ViewModel Example

```kotlin
@HiltViewModel
class TriviaViewModel @Inject constructor(
    private val getDailyQuestionUseCase: GetDailyQuestionUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TriviaUiState())
    val uiState: StateFlow<TriviaUiState> = _uiState.asStateFlow()
    
    private var timerJob: Job? = null
    
    init {
        loadDailyQuestion()
    }
    
    private fun loadDailyQuestion() {
        viewModelScope.launch {
            getDailyQuestionUseCase().collect { result ->
                result.onSuccess { question ->
                    _uiState.update { it.copy(question = question) }
                    startTimer(question.timeLimit)
                }
            }
        }
    }
    
    private fun startTimer(totalSeconds: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var remaining = totalSeconds
            while (remaining > 0) {
                _uiState.update { it.copy(timeRemaining = remaining) }
                delay(1000)
                remaining--
            }
            // Auto-submit on timeout
            submitAnswer()
        }
    }
    
    fun selectAnswer(index: Int) {
        _uiState.update { it.copy(selectedAnswerIndex = index) }
    }
    
    fun submitAnswer() {
        timerJob?.cancel()
        val currentState = _uiState.value
        val question = currentState.question ?: return
        val selectedIndex = currentState.selectedAnswerIndex ?: -1
        
        viewModelScope.launch {
            val timeSpent = question.timeLimit - (currentState.timeRemaining ?: 0)
            submitAnswerUseCase(
                questionId = question.id,
                selectedAnswerIndex = selectedIndex,
                timeSpent = timeSpent
            )
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

data class TriviaUiState(
    val question: TriviaQuestion? = null,
    val selectedAnswerIndex: Int? = null,
    val timeRemaining: Int? = null,
    val isLoading: Boolean = false
)
```

---

## 🔧 Dependencies Setup

### Update `libs.versions.toml`

```toml
[versions]
agp = "8.13.2"
kotlin = "2.0.21"
coreKtx = "1.17.0"
junit = "4.13.2"
junitVersion = "1.3.0"
espressoCore = "3.7.0"
lifecycleRuntimeKtx = "2.8.7"
activityCompose = "1.9.3"
composeBom = "2024.12.01"
hilt = "2.51.1"
hiltNavigationCompose = "1.2.0"
room = "2.6.1"
datastore = "1.1.1"
navigation = "2.8.5"
coroutines = "1.9.0"
firebaseBom = "32.7.4"
ksp = "2.0.21-1.0.28"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }

# Hilt
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-android-compiler", version.ref = "hilt" }
hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "hiltNavigationCompose" }

# Room
room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }

# DataStore
datastore-preferences = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "datastore" }

# Navigation
navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigation" }

# Coroutines
kotlinx-coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines" }
kotlinx-coroutines-play-services = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-play-services", version.ref = "coroutines" }

# Firebase
firebase-bom = { group = "com.google.firebase", name = "firebase-bom", version.ref = "firebaseBom" }
firebase-firestore-ktx = { group = "com.google.firebase", name = "firebase-firestore-ktx" }
firebase-auth-ktx = { group = "com.google.firebase", name = "firebase-auth-ktx" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
hilt-android = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
google-services = { id = "com.google.gms.google-services", version = "4.4.0" }
```

### Update `app/build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    // Uncomment when using Firebase
    // alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.quizzy"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.quizzy"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        // Room schema export
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    
    kotlinOptions {
        jvmTarget = "11"
    }
    
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    
    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    
    // Navigation
    implementation(libs.navigation.compose)
    
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    
    // DataStore
    implementation(libs.datastore.preferences)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)
    
    // Firebase (optional)
    // implementation(platform(libs.firebase.bom))
    // implementation(libs.firebase.firestore.ktx)
    // implementation(libs.firebase.auth.ktx)
    
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
```

---

## 🌟 Extra Considerations

### Accessibility

```kotlin
// Add content descriptions to all interactive elements
Icon(
    imageVector = Icons.Default.Person,
    contentDescription = "Open profile"
)

// Use semantic properties
Text(
    text = "Score: $score",
    modifier = Modifier.semantics {
        contentDescription = "Your current score is $score points"
    }
)

// Ensure touch targets are at least 48dp
Button(
    modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 48.dp)
) {
    Text("Submit")
}
```

### Animations

```kotlin
// Smooth transitions between screens
@Composable
fun QuizzyNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = { fadeIn() + slideInHorizontally() },
        exitTransition = { fadeOut() + slideOutHorizontally() }
    ) {
        // Screen definitions
    }
}

// Animate score changes
@Composable
fun AnimatedScore(score: Int) {
    var currentScore by remember { mutableStateOf(0) }
    
    LaunchedEffect(score) {
        animate(
            initialValue = currentScore.toFloat(),
            targetValue = score.toFloat()
        ) { value, _ ->
            currentScore = value.toInt()
        }
    }
    
    Text(text = currentScore.toString())
}
```

### Offline Support

```kotlin
// Repository pattern ensures offline-first approach
class TriviaRepositoryImpl @Inject constructor(
    private val localDataSource: TriviaQuestionDao,
    private val remoteDataSource: TriviaApiService,
    private val networkMonitor: NetworkMonitor
) : TriviaRepository {
    
    override fun getDailyQuestion(date: LocalDate): Flow<Result<TriviaQuestion>> = flow {
        // Try local first
        val local = localDataSource.getQuestionByDate(date)
        if (local != null) {
            emit(Result.success(local.toDomain()))
        }
        
        // Fetch from remote if online
        if (networkMonitor.isOnline()) {
            try {
                val remote = remoteDataSource.getDailyQuestion(date)
                localDataSource.insertQuestion(remote.toEntity())
                emit(Result.success(remote.toDomain()))
            } catch (e: Exception) {
                if (local == null) {
                    emit(Result.failure(e))
                }
            }
        }
    }
}
```

### Notifications (Daily Reminder)

```kotlin
// WorkManager for daily notifications
class DailyReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("New Trivia Question!")
            .setContentText("Your daily challenge awaits. Can you keep the streak?")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        
        notificationManager.notify(NOTIFICATION_ID, notification)
        
        return Result.success()
    }
}

// Schedule daily work
class QuizzyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        scheduleDailyReminder()
    }
    
    private fun scheduleDailyReminder() {
        val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(
            1, TimeUnit.DAYS
        )
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()
        
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_reminder",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyWorkRequest
        )
    }
}
```

---

## 📝 Implementation Checklist

### Phase 1: Foundation (Week 1)
- [ ] Setup Jetpack Compose with Material 3
- [ ] Configure Hilt for dependency injection
- [ ] Setup Room database with all entities
- [ ] Create DataStore for preferences
- [ ] Implement navigation graph
- [ ] Create theme (light/dark mode)

### Phase 2: Core Features (Week 2)
- [ ] Implement data models and repositories
- [ ] Create Home Screen with streak display
- [ ] Build Trivia Question Screen with timer
- [ ] Implement Result Screen
- [ ] Add scoring logic
- [ ] Implement streak calculation

### Phase 3: Gamification (Week 3)
- [ ] Design and implement badge system
- [ ] Create Badge Gallery Screen
- [ ] Add badge unlock animations
- [ ] Implement Profile Screen
- [ ] Add statistics tracking

### Phase 4: Social Features (Week 4)
- [ ] Implement Leaderboard Screen
- [ ] Add local/weekly/all-time rankings
- [ ] Create leaderboard sync logic
- [ ] Add user profile management

### Phase 5: Polish & Backend (Week 5)
- [ ] Integrate Firebase (optional)
- [ ] Add smooth animations
- [ ] Implement daily notifications
- [ ] Add accessibility features
- [ ] Perform testing and bug fixes
- [ ] Optimize performance

### Phase 6: Testing & Release (Week 6)
- [ ] Unit tests for business logic
- [ ] UI tests for critical flows
- [ ] Integration tests for database
- [ ] Performance testing
- [ ] Prepare for Play Store release

---

## 🚀 Getting Started

1. **Update dependencies** in `build.gradle.kts` and `libs.versions.toml`
2. **Configure Hilt** by creating `QuizzyApplication.kt`
3. **Setup Room database** with all entities and DAOs
4. **Create base navigation** structure
5. **Implement theme** with light/dark mode support
6. **Build screens incrementally** starting with Home → Trivia → Result
7. **Add business logic** for scoring, streaks, and badges
8. **Integrate backend** (Firebase or mock service)
9. **Polish UI/UX** with animations and accessibility
10. **Test thoroughly** before release

---

## 📚 Additional Resources

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Firebase Firestore](https://firebase.google.com/docs/firestore)
- [Material Design 3](https://m3.material.io/)
- [Android Architecture Components](https://developer.android.com/topic/architecture)

---

**This specification provides a complete roadmap for building Quizzy. Start with Phase 1 and work through incrementally, testing each feature as you go. Good luck! 🎯**
