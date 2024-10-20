package com.example.gameon.transformers

import com.example.gameon.models.HeadlinesModel
import com.example.gameon.models.HeadlinesModelList
import com.example.gameon.models.HeadlinesResponse
import retrofit2.Response

class HeadlinesTransformer {

    fun transformHeadlinesResponse(headlinesResponse: Response<List<HeadlinesResponse>>): HeadlinesModelList {
        val headlines = headlinesResponse.body()?.flatMap { response ->
            response.betViews.map { betView ->
                HeadlinesModel(
                    competitor1Caption = betView.competitor1Caption,
                    competitor2Caption = betView.competitor2Caption,
                    startTime = betView.startTime
                )
            }
        } ?: emptyList()

        return HeadlinesModelList(headlinesList = headlines)
    }

}