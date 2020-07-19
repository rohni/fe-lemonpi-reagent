# lemonpi-reagent

This is the beginning of a clojurescript frontend project for an assignment for
Lemonpi.

- advertisers api call
- stats api call
- Display the desired data in a table
- Style table and page 

## Still to do
- Sorting columns (click and it will sort)
- Persisting sort params in url
- Tests

## Notes
There is room for a lot of improvement.
- tests would be good.  I have been searching for any docs on how to test
  reagent components.  i.e. why is my ^{:key ...} not appearing in my data
  rows?!?
- Sorting is coming, but it is time for bed
- Styling needs a better solution using something modular.  (I bumped into
  garden, but have to look at it more closely)
- I'm not that happy with the check and merge mechanism to add the `clicks` and
  `impressions` values from stats.  I will see if core.async can solve this
  problem elegantly.  React's `useEffect` works for this really well.  And
  Rxjs's switch- and mergeMap.  There must be something, I just have to poke
  around and figure out how this has been solved.

## Development mode

To start the Figwheel compiler, navigate to the project folder and run the following command in the terminal:

```
lein figwheel
```

Figwheel will automatically push cljs changes to the browser. The server will be available at [http://localhost:3449](http://localhost:3449) once Figwheel starts up. 

Figwheel also starts `nREPL` using the value of the `:nrepl-port` in the `:figwheel`
config found in `project.clj`. By default the port is set to `7002`.

The figwheel server can have unexpected behaviors in some situations such as when using
websockets. In this case it's recommended to run a standalone instance of a web server as follows:

```
lein do clean, run
```

The application will now be available at [http://localhost:3000](http://localhost:3000).


### Optional development tools

Start the browser REPL:

```
$ lein repl
```
The Jetty server can be started by running:

```clojure
(start-server)
```
and stopped by running:
```clojure
(stop-server)
```


## Building for release

```
lein do clean, uberjar
```

## Deploying to Heroku

Make sure you have [Git](http://git-scm.com/downloads) and [Heroku toolbelt](https://toolbelt.heroku.com/) installed, then simply follow the steps below.

Optionally, test that your application runs locally with foreman by running.

```
foreman start
```

Now, you can initialize your git repo and commit your application.

```
git init
git add .
git commit -m "init"
```
create your app on Heroku

```
heroku create
```

optionally, create a database for the application

```
heroku addons:add heroku-postgresql
```

The connection settings can be found at your [Heroku dashboard](https://dashboard.heroku.com/apps/) under the add-ons for the app.

deploy the application

```
git push heroku master
```

Your application should now be deployed to Heroku!
For further instructions see the [official documentation](https://devcenter.heroku.com/articles/clojure).
