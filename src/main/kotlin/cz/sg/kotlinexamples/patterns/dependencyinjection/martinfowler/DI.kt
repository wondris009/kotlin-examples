//package cz.sg.kotlinexamples.dependencyinjection.martinfowler
//
//// first implementation - hardcoded movieFinder
//class MovieLister1 {
//
//    private val movieFinder = ColonDelimitedMovieFinder("movies.txt")
//
//    fun moviesDirectedBy(directorName: String): List<Movie> {
//        val allMovies = movieFinder.findAll()
//        return allMovies.filter { it.directorName == directorName}
//    }
//}
//
//// to be more flexible and use any movieFinder, lets define interface
//interface MovieFinder {
//    fun findAll(): List<Movie>
//}
//
////now how to add it to MovieLister? we can use constructor initialization
//class Movie2Lister2 {
//    private lateinit var movieFinder: MovieFinder
//
//    init {
//        movieFinder = ColonDelimitedMovieFinder("movies1.txt")
//    }
//    //......
//}
//
//class ColonDelimitedMovieFinder(fileName: String) : MovieFinder {
//    override fun findAll(): List<Movie> {
//        return getFile(fileName).mapToMovies()
//    }
//}
//
//// if only myself want to use MovieLister class it's ok
//
//// thanks to defining MovieFinder as interface i haven't to change MovieLister class - perfect
//
//// MovieLister is now dependent on MovieFinder interface and on it's implementation
//
//// but what if other team wants to use MovieLister class too, but their films are not in text file but in DB / XML file etc. ?
//
//// how to choose correct implementation in runtime? (see we choose it in init {} method, which is not what we want, we want to do it based on some configuration, eg.for this profile use ColonDelimitedMovieFinder, for another profile use XmlMovieFinder)
//
//// Martin describes solving this in runtime using component called "Plugin"
//    // implementation ISN'T linked to MovieLister at compile time
//    // problem how to make that MovieLister class IGNORES the MovieFinder implementation but still can TALK to the implementation
//    // and this is work for Inversion of Control
//
//
//
//
//
