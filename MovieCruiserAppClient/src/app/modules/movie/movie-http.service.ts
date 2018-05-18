import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map,retry } from 'rxjs/operators'
import { Movie } from './movie';
import { TmdbMovie } from './tmdbMovie'
import { catchError } from 'rxjs/operators/catchError';
import {of} from 'rxjs/observable/of'
import { tap } from 'rxjs/operators/tap';
import { AuthenticationService } from '../authentication/authentication.service';

@Injectable()
export class MovieHttpService {


  
  constructor(private httpClient: HttpClient,
  private authService: AuthenticationService) { }


  //watchList
  springApiEndpoint:string = "http://localhost:8081/api/v1/movies";

  getWatchListedMovies():Observable<Array<Movie>>{
    return this.httpClient.get<Array<Movie>>(this.springApiEndpoint,
    {headers:
      {"Authorization":"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ"}
    }
  );
  }


  saveWatchListMovies(movie){
    console.log([movie]);
    movie.overview = (movie.overview).substring(0,250)+"...";
    return this.httpClient.post(this.springApiEndpoint,movie,
      {headers:
        {"Authorization":"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ"}
      }
    );
  }


  updateMovie(movie:Movie):Observable<Movie>{
    return this.httpClient.put<Movie>(`${this.springApiEndpoint}/${movie.id}`,movie,
    {headers:
      {"Authorization":"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ"}
    }
  );
  }

  deleteMovie(movie:Movie){
    return this.httpClient.delete(`${this.springApiEndpoint}/${movie.id}`,
      {headers:
        {"Authorization":"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ"}
     ,responseType:'text'});
  }

  getMovie(id: number):Observable<Movie> {
    let obs = this.httpClient.get<Movie>(`${this.springApiEndpoint}/${id}`,
    {headers:
      {"Authorization":"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYTExMSIsImlhdCI6MTUyNTcwMTE1OH0.fkQE9eKW7tqAudiiT8XFYvNnfW5-GIB9GfH9x1Ym0oQ"}
    }
  );
    console.log(obs);
    return obs.pipe(
      tap(_=> console.log('movie got in service')),
      map(this.returnMovie.bind(this))
    );
  }

  returnMovie(movie:Movie){
    return movie;
  }
}
