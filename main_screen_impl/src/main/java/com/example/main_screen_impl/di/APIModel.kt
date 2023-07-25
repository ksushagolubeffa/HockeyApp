package com.example.main_screen_impl.di

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("dates")
    val dates: List<DateModel>
)

data class DateModel(
    @SerializedName("date")
    val date: String,
    @SerializedName("totalGames")
    val totalGames: Int,
    @SerializedName("games")
    val games: List<GameModel>
)

data class GameModel(
    @SerializedName("gameType")
    val gameType: String,
    @SerializedName("gamePk")
    val id: Long,
    @SerializedName("linescore")
    val linescore: LinescoreModel
)

data class LinescoreModel(
    @SerializedName("currentPeriod")
    val currentPeriod: Int,
    @SerializedName("periods")
    val periods: List<PeriodModel>,
    @SerializedName("teams")
    val teams: MatchModel
)

data class PeriodModel(
    @SerializedName("periodType")
    val periodType: String,
    @SerializedName("ordinalNum")
    val ordinalNum: String,
    @SerializedName("home")
    val home: TeamPeriodInfo,
    @SerializedName("away")
    val away: TeamPeriodInfo
)

data class TeamPeriodInfo(
    @SerializedName("goals")
    val goals: Int,
    @SerializedName("shotsOnGoal")
    val shotsOnGoal: Int,
    @SerializedName("rinkSide")
    val rinkSide: String
)

data class MatchModel(
    @SerializedName("away")
    val away: TeamModel,
    @SerializedName("home")
    val home: TeamModel
)

data class TeamModel(
    @SerializedName("goals")
    val goals: Int,
    @SerializedName("team")
    val team: TeamInfo,
    @SerializedName("shotsOnGoal")
    val shotsOnGoal: Int,
    @SerializedName("goaliePulled")
    val goaliePulled: Boolean,
    @SerializedName("powerPlay")
    val powerPlay: Boolean
)

data class TeamInfo(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)