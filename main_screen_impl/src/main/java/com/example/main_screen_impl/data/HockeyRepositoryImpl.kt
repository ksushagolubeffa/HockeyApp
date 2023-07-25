package com.example.main_screen_impl.data

import android.util.Log
import com.example.main_screen_api.interfaces.HockeyRepository
import com.example.main_screen_api.model.DateEntity
import com.example.main_screen_api.model.GameEntity
import com.example.main_screen_api.model.LinescoreEntity
import com.example.main_screen_api.model.MatchEntity
import com.example.main_screen_api.model.PeriodEntity
import com.example.main_screen_api.model.ResponseEntity
import com.example.main_screen_api.model.TeamEntity
import com.example.main_screen_impl.di.HockeyAPI
import com.example.main_screen_impl.di.DateModel
import com.example.main_screen_impl.di.GameModel
import com.example.main_screen_impl.di.LinescoreModel
import com.example.main_screen_impl.di.MatchModel
import com.example.main_screen_impl.di.PeriodModel
import com.example.main_screen_impl.di.TeamInfo
import com.example.main_screen_impl.di.TeamModel
import com.example.main_screen_impl.di.TeamPeriodInfo

class HockeyRepositoryImpl(
    private val api: HockeyAPI
) : HockeyRepository {
    override suspend fun getGamesByDate(date: String): ResponseEntity {
        Log.e("Repository Impl", api.getByDate(date).dates.toString())
        return ResponseEntity(
            convertToDateEntity(api.getByDate(date).dates)
        )
    }

    private fun convertToDateEntity(dates: List<DateModel>): List<DateEntity> {
        val list = ArrayList<DateEntity>()
        dates.forEach {
            list.add(
                DateEntity(
                    it.date,
                    it.totalGames,
                    convertToGameEntity(it.games)
                )
            )
        }
        return list
    }

    private fun convertToGameEntity(games: List<GameModel>): List<GameEntity> {
        val list = ArrayList<GameEntity>()
        games.forEach {
            list.add(
                GameEntity(
                    it.gameType,
                    it.id,
                    convertToLinescoreEntity(it.linescore)
                )
            )
        }
        return list
    }

    private fun convertToLinescoreEntity(linescore: LinescoreModel): LinescoreEntity {
        return LinescoreEntity(
            linescore.currentPeriod,
            convertToPeriodEntity(linescore.periods),
            convertToMatchEntity(linescore.teams)
        )
    }

    private fun convertToPeriodEntity(periods: List<PeriodModel>): List<PeriodEntity>{
        val list = ArrayList<PeriodEntity>()
        periods.forEach {
            list.add(
                PeriodEntity(
                    it.periodType,
                    it.ordinalNum,
                    convertToTeamPeriodInfo(it.home),
                    convertToTeamPeriodInfo(it.away)
                )
            )
        }
        return list
    }

    private fun convertToTeamPeriodInfo(team: TeamPeriodInfo): com.example.main_screen_api.model.TeamPeriodInfo{
        return com.example.main_screen_api.model.TeamPeriodInfo(
            team.goals,
            team.shotsOnGoal,
            team.rinkSide
        )
    }

    private fun convertToMatchEntity(match: MatchModel): MatchEntity{
        return MatchEntity(
            covertToTeamEntity(match.away),
            covertToTeamEntity(match.home)
        )
    }

    private fun covertToTeamEntity(team: TeamModel): TeamEntity {
        return TeamEntity(
            team.goals,
            convertToTeamInfo(team.team),
            team.shotsOnGoal,
            team.goaliePulled,
            team.powerPlay
        )
    }

    private fun convertToTeamInfo(info: TeamInfo): com.example.main_screen_api.model.TeamInfo{
        return com.example.main_screen_api.model.TeamInfo(
            info.id,
            info.name
        )
    }
}
