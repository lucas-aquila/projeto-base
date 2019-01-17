import { Injectable } from '@angular/core';
import { Describer } from '../../application/describer/describer';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BASE_URL } from './base-url';


//const url = `${BASE_URL}/usuarios`;

@Injectable()
export class WildcardService {

    /**
     * 
     * @param http 
     */
    constructor(private http: HttpClient) {}

    public call(url,obj):Promise<any>{
        let params = new HttpParams();
        const keys = Object.keys(obj);
        console.log(keys);
        for (let key of keys){
            if ( key=="page" || key=="pageable" ){
                params = Describer.getHttpParamsFromPageable(params, obj[key]);
            } else {
            params = params.set(key, obj[key]);
            }
        }

        return this.http.get<any>(url, { params }).toPromise()
        .then(response => response.json());
    }

    public callPost(url,obj):Promise<any>{
        let params = new HttpParams();
        /*const keys = Object.keys(obj);
        for (let key of keys){
            if ( key=="page" || key=="pageable" ){
                params = Describer.getHttpParamsFromPageable(params, obj[key]);
            } else {
            params = params.set(key, obj[key]);
            }
        }*/

        return this.http.post<any>(url, obj).toPromise()
        .then(response => response.json());
    }



/*
    public findUsuarioById(id: number): Promise<Usuario> {
        return this.http.get<any>(`${url}/${id}`)
            .toPromise()
            .then(response => response.json());
    }

    public listUsuariosByFilters(filter: string = '', pageable: any): Promise<any> {
        let params = new HttpParams();
        params = params.set('filter', filter);
        params = Describer.getHttpParamsFromPageable(params, pageable);

        return this.http.get<Usuario[]>(url, { params }).toPromise();
    }

    public insertUsuario(usuario: Usuario): Promise<Usuario> {
        return this.http.post<any>(url, usuario)
            .toPromise()
            .then(response => response.json());
    }


    public updateUsuario(usuario: Usuario): Promise<Usuario> {
        return this.http.put<any>(`${url}/${usuario.id}`, usuario)
            .toPromise()
            .then(response => response.json());
    }


    public deleteUsuario(id: number): Promise<void> {
        return this.http.delete<any>(`${url}/${id}`).toPromise();
    }
*/
    // public changePassword(usuarioId: number, novaSenha: string): Promise<any> {
    //     let params = new HttpParams();
    //     params = params.set('usuarioId', usuarioId ? usuarioId.toString() : '');
    //     params = params.set('novaSenha', novaSenha ? novaSenha : '');

    //     return this.httpClient
    //         .get(this.baseUrl + 'usuarios/' + usuarioId + '/alterar-senha/', {
    //             params: params
    //         })
    //         .toPromise()
    //         .then(result => result);
    // }

    // public changeMyPassword(
    //     usuarioId: number,
    //     senhaAtual: string,
    //     novaSenha: string
    // ): Promise<any> {
    //     let params = new HttpParams();
    //     params = params.set('usuarioId', usuarioId ? usuarioId.toString() : '');
    //     params = params.set('senhaAtual', senhaAtual ? senhaAtual : '');
    //     params = params.set('novaSenha', novaSenha ? novaSenha : '');

    //     return this.httpClient
    //         .get(
    //             this.baseUrl +
    //                 'usuarios/' +
    //                 usuarioId +
    //                 '/alterar-minha-senha/',
    //             {
    //                 params: params
    //             }
    //         )
    //         .toPromise();
    // }
}
