Now we"re going to start learning about jQuery ajax

    $ ->

First, we"ve got to make a link for the articles array. We identify the
`div` that has a role=content and then extract the contents of the data-content
element

      $articleListUrl = $('[role="content"]').data("url")
      $articleTemplate = $("article")
      $mycontent = $('[role="content"]')
      $("article").remove()

      $.ajax(
        url : $articleListUrl
        type : "get"
        dataType : "json")
        .done (data) ->
          console.log("request is done")
          $.each data, (idx, dat) ->
            $article = $articleTemplate.clone()
            $article.find("h3 a").attr(
              href : dat.slug
              text : dat.title)
            $article.find("h3 a").text(dat.title)
            $article.find("h6 span").text(
              new Date(dat.date.year, dat.date.month, dat.date.day))
            $article.append(dat.body).append("<hr>")
            $mycontent.append($article)
      
