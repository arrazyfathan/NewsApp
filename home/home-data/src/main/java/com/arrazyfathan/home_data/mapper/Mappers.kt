package com.arrazyfathan.home_data.mapper

import com.arrazyfathan.home_data.model.ArticleDto
import com.arrazyfathan.home_data.model.SourceDto
import com.arrazyfathan.home_data.source.local.entities.ArticleEntity
import com.arrazyfathan.home_data.source.local.entities.SourceEntity
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_domain.model.Source

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

fun ArticleEntity.toDomainArticle(): Article {
    return Article(
        author = this.author.orEmpty(),
        content = this.content.orEmpty(),
        description = this.description.orEmpty(),
        publishedAt = this.publishedAt.orEmpty(),
        source = this.source.toDomainSource(),
        title = this.title.orEmpty(),
        url = this.url.orEmpty(),
        urlToImage = this.urlToImage.orEmpty(),
        isBookmarked = this.isBookmarked
    )
}

fun SourceEntity.toDomainSource(): Source {
    return Source(name = this.name)
}

fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = this.source.toSourceEntity(),
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage,
        isBookmarked = this.isBookmarked
    )
}

fun Source.toSourceEntity(): SourceEntity {
    return SourceEntity(name = this.name)
}
