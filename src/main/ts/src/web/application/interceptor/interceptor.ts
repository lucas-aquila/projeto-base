
import {throwError as observableThrowError,  Observable } from 'rxjs';

import {catchError, tap} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import {
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest
} from '@angular/common/http';



import { Router } from '@angular/router';
import { MessageService } from '../../domain/services/message.service';

@Injectable()
export class Interceptor implements HttpInterceptor {
    /**
     * Instancia a partir do window o NProgress
     */
    progress = window['NProgress'];

    /**
     *
     * @param {MatSnackBar} snackBar
     * @param {Router} router
     */
    constructor(public messageService: MessageService, public router: Router) {}

    /**
     * Intercepta todas as requisições
     * @param {HttpRequest<>} req
     * @param {HttpHandler} next
     * @returns {Observable<HttpEvent<>>}
     */
    intercept(
        req: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {
        /**
         * Inicia o progress
         */
        this.progress.start();

        /**
         * Finaliza o progress
         */
        return next
            .handle(req).pipe(
            tap(this.progress.done()),
            catchError(this.catchErrors()),);
    }

    /**
     * Função privada, captura os erros
     * @returns {(res: any) => ErrorObservable}
     */
    private catchErrors() {
        /**
         * Encerra progress
         */
        this.progress.done();

        return (res: any) => {
            if (res.status === 500) {
                this.error(res.error.message);
            }

            if (res.status === 401 || res.status === 403) {
                //handle authorization errors
                //in this example I am navigating to logout route which brings the login screen
                this.router.navigate(['login']);
            }

            return observableThrowError(res);
        };
    }

    /**
     *
     * @param message
     */
    public error(message: string) {
        this.messageService.toastWarning(message);
    }
}
