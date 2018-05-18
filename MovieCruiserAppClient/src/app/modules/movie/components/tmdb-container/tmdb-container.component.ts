import { Component, OnInit } from '@angular/core';
import { Movie } from '../../movie';
import { MovieService } from '../../movie.service';
import { ActivatedRoute } from '@angular/router'
import { switchMap } from 'rxjs/operators';
import { ParamMap } from '@angular/router';
import { OnChanges } from '@angular/core/src/metadata/lifecycle_hooks';
import { forEach } from '@angular/router/src/utils/collection';

@Component({
  selector: 'movie-tmdb-container',
  template: `<movie-container [movies]="movies" [useWatchlistApi]="useWatchlistApi" (updateMovies)="updateMovies($event)"></movie-container>`,
})
export class TmdbContainerComponent implements OnInit {

  movies: Array<Movie>;
  movieType: string;
  useWatchlistApi = false;
  

  constructor(private movieService: MovieService, private actRoute: ActivatedRoute) {
    this.movies = [];
    console.log(this.actRoute);
    this.actRoute.data.subscribe(
      (data) => this.movieType = data.type
    );
  }
  ngOnInit() {
    this.movieService.getMovies(this.movieType, '2')
      .subscribe((movies) => {
        for(let movie of movies){
                let transformMovie = <Movie>{};
                transformMovie.movie_id = movie.id;
                transformMovie.title = movie.title;
                transformMovie.overview = movie.overview;
                transformMovie.comments = movie.comments;
                transformMovie.poster_path = movie.poster_path;
                transformMovie.release_date = movie.release_date;
                transformMovie.detailId = movie.id;
                this.movies.push(transformMovie);
              }
            });
    }
}
