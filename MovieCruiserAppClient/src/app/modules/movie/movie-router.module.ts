import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'
import { TmdbContainerComponent } from './components/tmdb-container/tmdb-container.component';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { SearchComponent } from './components/search/search.component'
import { DetailComponent } from './components/detail/detail.component'
import { AuthenticationGuardService } from '../authentication/authentication-guard.service'

const routes: Routes = [
    {
        path: 'movies',
        children: [
            { path: '', redirectTo: '/movies/popular', pathMatch: 'full' },
            { path: 'popular', component: TmdbContainerComponent, data: { type: 'popular' } },
            { path: 'top_rated', component: TmdbContainerComponent, data: { type: 'top_rated' } },
            { path: 'watchlist', component: WatchlistComponent, canActivate:[AuthenticationGuardService]},
            { path: 'search', component: SearchComponent},
            { path: 'detail', component: DetailComponent},
            
           
        ]
    }];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MovieRoutingModule {

}