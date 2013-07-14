The **scope** class is a tool to learn coffee script

    $ ->
      loadArticles = ->
        jqxhr = $.ajax('blog/2013',
          type: 'get'
          dataType: 'json')
        jqxhr.done (data, status, response) ->
          $.each data, ->
            post = '<article>'
              $('<h1/>').text( json.title ).appendTo('["role=content"]')
            $('<body/>').html( json.body ).appendTo('body')
        jqxhr.fail: ->
            console.log('Huston, we have a problem')
          complete: ->
            console.log('the article loading is complete')
        )
      
      loadTags = ->

