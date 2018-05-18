import { Component, OnInit } from '@angular/core';
import { Movie } from '../../movie';
import { MovieService } from '../../movie.service';
import { ContainerComponent } from '../container/container.component'
import { ActivatedRoute } from '@angular/router'
import { Subscription } from 'rxjs/Subscription';
@Component({
  selector: 'app-watchlist',
  template: `<movie-container [movies]="movies" [useWatchlistApi]="useWatchlistApi" (updateMovies)="updateMovies($event)"></movie-container>`,
})
export class WatchlistComponent implements OnInit {

  
  movies: Array<Movie>;
  movieType: string;
  useWatchlistApi = true;
  movieSubscription : Subscription;

  constructor(private movieService: MovieService, private actRoute: ActivatedRoute) {
    this.movies = [];
    this.actRoute.data.subscribe(
      (data) => this.movieType = data.type
    );

  }
  ngOnInit() {
    
    this.movieSubscription = this.movieService.getWatchListedMovies()
      .subscribe((movies) => {
        for(let movie of movies){
          movie.detailId = movie.id;
          this.movies.push(movie);
        }
      });
  }


  updateMovies(e):void{
    console.log('update trigger received in watchlist');
    this.movieSubscription.unsubscribe();
    this.movies = [];
    this.movieSubscription = this.movieService.getWatchListedMovies()
      .subscribe((movies) => {
        for(let movie of movies){
          movie.detailId = movie.id;
          this.movies.push(movie);
        }
      });
  }
}


