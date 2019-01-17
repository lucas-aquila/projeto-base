import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';

import { WebModule } from '../domain/web.module';
import { environment } from '../../environments/environment';

import 'hammerjs';

if (environment.production) {
    enableProdMode();
}

platformBrowserDynamic().bootstrapModule(WebModule)
    .catch(err => console.log(err));
