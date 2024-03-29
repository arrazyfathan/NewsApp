package com.arrazyfathan.search_data.mapper

import com.arrazyfathan.search_data.model.ArticleDto
import com.arrazyfathan.search_data.model.SourceDto
import com.arrazyfathan.search_domain.model.Article
import com.arrazyfathan.search_domain.model.Source


/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */

fun ArticleDto.toDomainArticle(): Article {
    return Article(
        author = this.author.orEmpty(),
        content = this.content.orEmpty(),
        description = this.description.orEmpty(),
        publishedAt = this.publishedAt.orEmpty(),
        source = this.source.toDomainSource(),
        title = this.title.orEmpty(),
        url = this.url.orEmpty(),
        urlToImage = this.urlToImage.orEmpty(),
    )
}

fun SourceDto.toDomainSource(): Source {
    return Source(
        name = this.name,
    )
}
