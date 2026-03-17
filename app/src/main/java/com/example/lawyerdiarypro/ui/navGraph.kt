package com.example.lawyerdiarypro.ui


import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lawyerdiarypro.ui.presentation.home.Case.CaseDetailsScreen
import com.example.lawyerdiarypro.ui.presentation.home.Case.CreateCaseScreen
import com.example.lawyerdiarypro.ui.presentation.home.HomeScreen
import com.example.lawyerdiarypro.ui.presentation.Login.SignInScreen
import com.example.lawyerdiarypro.ui.presentation.Login.SignUpScreen
import com.example.lawyerdiarypro.ui.presentation.Login.SplashScreen
import com.example.lawyerdiarypro.ui.presentation.Login.WelcomeScreen

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Splash : Screen("splash")
    object SignUp : Screen("signup")
    object SignIn : Screen("signin")
    object Home : Screen("home")
    object CreateCase : Screen("create_case") // Add this

    object CaseDetails : Screen("details/{caseId}") {
        fun passId(id: Int) = "details/$id"
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    // Spring animation configuration for smooth transitions
    // Change <Float?> to <IntOffset>
    val springSpec: FiniteAnimationSpec<IntOffset> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = springSpec
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = springSpec
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = springSpec
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = springSpec
            )
        }
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(route = Screen.SignIn.route) {
            SignInScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        // Inside your SetupNavGraph function's NavHost block:
        composable(route = Screen.CreateCase.route) {
            CreateCaseScreen(navController)
        }
        composable(
            route = Screen.CaseDetails.route,
            arguments = listOf(navArgument("caseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("caseId") ?: 0
            CaseDetailsScreen(caseId = id, navController = navController)
        }
        composable(
            route = "create_case?caseId={caseId}", // The '?' makes it optional
            arguments = listOf(navArgument("caseId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("caseId") ?: -1
            CreateCaseScreen(navController = navController, caseId = if(id == -1) null else id)
        }
    }
}