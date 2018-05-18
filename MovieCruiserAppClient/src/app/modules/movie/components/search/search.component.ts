import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import {ContainerComponent} from '../container/container.component'
import { Movie } from '../../movie';
import { MovieService } from '../../movie.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { debounceTime } from 'rxjs/operators/debounceTime';
import { distinctUntilChanged } from 'rxjs/operators/distinctUntilChanged';
import { switchMap } from 'rxjs/operators/switchMap';
@Component({
  selector: 'movie-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  movies: Array<Movie>;
  searchKey$ = new Subject<string>();
  str = '';

  constructor(private movieService: MovieService, private actRoute: ActivatedRoute) {
    this.movies = [];
    this.searchMovies(this.searchKey$).subscribe(
      (movies)=> {
        this.movies = [];
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
   

  ngOnInit() {

  }

  searchMovies(term : Observable<string>){
    return term.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap( (terms)=>{
        console.log(terms);
        this.str =terms;
          return this.movieService.searchMovies(terms);
      })
    )
  }


  

}
