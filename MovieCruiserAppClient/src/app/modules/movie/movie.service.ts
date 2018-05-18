import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, retry } from 'rxjs/operators'
import { Movie } from './movie';
import { TmdbMovie } from './tmdbMovie'
import { catchError } from 'rxjs/operators/catchError';
import { of } from 'rxjs/observable/of'
import { tap } from 'rxjs/operators/tap';
import { MovieHttpService } from './movie-http.service';
import { AuthenticationService } from '../authentication/authentication.service';
import { AuthenticationGuardService } from '../authentication/authentication-guard.service';
import { AlertService } from '../alert/alert.service';
@Injectable()
export class MovieService {

  tmdbEndPoint: string;
  posterPathPrefix: string;
  movieDbApiKey: string = 'api_key=82e157247804b2bf2707ca53349457d1';
  languageParam: string = 'language=en-US';
  pageParam: string = 'page=';
  //watchList
  springApiEndpoint: string = "http://localhost:8081/api/v1/movies";


  constructor(
    private httpClient: HttpClient,
    private authService: AuthenticationService,
    private authGuardService: AuthenticationGuardService,
    private alertService:AlertService) {
    this.tmdbEndPoint = 'https://api.themoviedb.org/3/movie';
    this.posterPathPrefix = 'https://image.tmdb.org/t/p/w500';
  }


  getMovies(type: string, page: string = '1'): Observable<Array<TmdbMovie>> {
    let url = `${this.tmdbEndPoint}/${type}?${this.movieDbApiKey}&${this.languageParam}&${this.pageParam}${page}`;
    return this.httpClient.get(url).pipe(
      map(this.pickMovieResults),
      map(this.transformPosterPath.bind(this)),
      catchError(this.handleError<any>(`Retrieve ${type} movies`,[]))
    );
  }


  transformPosterPath(movies): Array<Movie> {
    return movies.map(movie => {
      movie.poster_path = `${this.posterPathPrefix}${movie.poster_path}`;
      return movie;
    });
  }


  pickMovieResults(res: Response) {
    return res['results'];
  }

  //https://api.themoviedb.org/3/search/movie?api_key=fgfg&language=en-US&page=1&include_adult=false
  searchMovies(searchKey: string, page: string = '1'): Observable<Array<TmdbMovie>> {

    if (searchKey.length > 0) {
      let url = `https://api.themoviedb.org/3/search/movie?${this.movieDbApiKey}&${this.languageParam}&${this.pageParam}${page}&query=${searchKey}&include_adult=false`;
      return this.httpClient.get(url).pipe(
        retry(3),
        map(this.pickMovieResults),
        map(this.transformPosterPath.bind(this)),
        catchError(this.handleError<any>(`Search movies`,[]))
      );
    } else {
      return Observable.of([]);
    }
  }
  //https://api.themoviedb.org/3/movie/11216?api_key=82e157247804b2bf2707ca53349457d1&language=en-US
  getMovieDetail(id: number): Observable<Movie> {
    let url = `${this.tmdbEndPoint}/${id}?${this.movieDbApiKey}&${this.languageParam}`;
    return this.httpClient.get(url).pipe(
      map(this.transformPosterPathMovie.bind(this)),
      catchError(this.handleError<any>(`Retrieve movie detail`))
    );
  }

  transformPosterPathMovie(movie): Movie {
    movie.poster_path = `${this.posterPathPrefix}${movie.poster_path}`;
    return movie;
  };


  // spring api calls

  getAuthHeader() {
    return {
      headers: { "Authorization": "Bearer " + this.authService.getToken() }
    }
  }

  getWatchListedMovies(): Observable<Array<Movie>> {
    if (this.authGuardService.canActivate) {
      return this.httpClient.get<Array<Movie>>(this.springApiEndpoint,
        this.getAuthHeader()
      ).pipe(
        catchError(this.handleSpringApiError<any>(`Retrieve watchlist movie`,[]))
      );
    }
  }
  saveWatchListMovies(movie) {
    if (this.authGuardService.canActivate) {
      movie.overview = (movie.overview).substring(0, 250) + "...";
      return this.httpClient.post(this.springApiEndpoint, movie,
        this.getAuthHeader()
      ).pipe(
        catchError(this.handleSpringApiError<any>(`save watchlist movie`,[]))
     
      );
    }
  }



  updateMovie(movie: Movie): Observable<Movie> {
    if (this.authGuardService.canActivate) {
      return this.httpClient.put<Movie>(`${this.springApiEndpoint}/${movie.id}`, movie,
        this.getAuthHeader()
      ).pipe(
        catchError(this.handleSpringApiError<any>(`update comment `,[]))
     
      );
    }
  }

  deleteMovie(movie: Movie) {
    if (this.authGuardService.canActivate) {
      return this.httpClient.delete(`${this.springApiEndpoint}/${movie.id}`,
        {
          headers: { "Authorization": "Bearer " + this.authService.getToken() },
          responseType: 'text'
        }
      ).pipe(
        catchError(this.handleSpringApiError<any>(`delete watchlist movie`,[]))
      );
    }
  }

  getMovie(id: number): Observable<Movie> {
    if (this.authGuardService.canActivate) {
      let obs = this.httpClient.get<Movie>(`${this.springApiEndpoint}/${id}`,
        this.getAuthHeader()
      );
      console.log(obs);
      return obs.pipe(
        tap(_ => console.log('movie got in service')),
        map(this.returnMovie.bind(this)),
        catchError(this.handleSpringApiError<any>(`Retrieve movie detail`,[]))
      );
    }
  }

  returnMovie(movie: Movie) {
    return movie;
  }


   //Handle Error
   private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      this.alertService.error(`${operation} failed => ${error.statusText}`);
      return Observable.of(result as T);
    }
  }

    //Handle Error
   private handleSpringApiError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      this.alertService.error(`${operation} failed => ${error.error.message}`);
      return Observable.of(result as T);
    }
  }




}