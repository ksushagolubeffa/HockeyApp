package com.example.main_screen_api.model

data class ResponseEntity(
    val dates: List<DateEntity>
)

data class DateEntity(
    val date: String,
    val totalGames: Int,
    val games: List<GameEntity>
)

data class GameEntity(
    val gameType: String, // тип игры
    val id: Long,
    val linescore: LinescoreEntity
)

data class LinescoreEntity(
    val currentPeriod: Int, // текущий период (понять, идет игра или нет)
    val periods: List<PeriodEntity>, //инфа по периодам
    val teams: MatchEntity // общая инфа по командам
)

data class PeriodEntity(
    val periodType: String, //тип периода (регулар, какой-то еще, хз)
    val ordinalNum: String, //номер периода
    val home: TeamPeriodInfo, // домашняя команда
    val away: TeamPeriodInfo // гости
)

data class TeamPeriodInfo(
    val goals: Int, // голы команды в периоде
    val shotsOnGoal: Int, // удары по воротам
    val rinkSide: String // сторона ринга
)

data class MatchEntity(
    val away: TeamEntity,
    val home: TeamEntity
)

data class TeamEntity(
    val goals: Int, // всего голов
    val team: TeamInfo, // инфа о команде
    val shotsOnGoal: Int, // всего ударов по воротам
    val goaliePulled: Boolean,
    val powerPlay: Boolean
)

data class TeamInfo(
    val id: Long,
    val name: String
)
