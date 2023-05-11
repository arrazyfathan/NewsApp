package com.arrazyfathan.common_utils.navigator

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */
sealed class Screen {
    object HomeActivity : Screen()
    object SearchActivity : Screen()
}
