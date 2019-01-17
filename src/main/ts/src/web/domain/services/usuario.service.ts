import { Injectable } from '@angular/core';
import { Describer } from '../../application/describer/describer';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BASE_URL } from './base-url';


const url = `${BASE_URL}/usuarios`;

export type Perfil = 'ADMINISTRADOR' | 'USUARIO' | 'GERENTE' | 'PATROCINADOR';

@Injectable()
export class UsuarioService {

    /**
     * 
     * @param http 
     */
    constructor(private http: HttpClient) {}

    /**
     * 
     * @param id 
     */
    public findUsuarioById(id: number): Promise<any> {
        return this.http.get(`${url}/${id}`).toPromise();
    }

    /**
     * 
     * @param filter 
     * @param organizacaoId 
     * @param status 
     * @param pageable 
     */
    public listUsuariosByFilters(filter: any = '', status: any = '', pageable: any = null): Promise<any> {
        let params = new HttpParams();
        params = params.set('filter', filter || '');
        params = params.set('status', status);
        params = Describer.getHttpParamsFromPageable(params, pageable);

        return this.http.get(url, { params }).toPromise();
    }

    /**
     * 
     * @param usuario 
     */
    public insertUsuario(usuario: any): Promise<any> {
        return this.http.post(url, usuario).toPromise();
    }

    /**
     * 
     * @param usuario 
     */
    public updateUsuario(usuario: any): Promise<any> {
        return this.http.put(`${url}/${usuario.id}`, usuario).toPromise();
    }

    /**
     * 
     * @param id 
     */
    public updateStatusUsuario(id: number): Promise<any> {
        return this.http.put(`${url}/status/${id}`, null).toPromise();
    }

    /**
     * 
     * @param id 
     */
    public deleteUsuario(id: number): Promise<void> {
        return this.http.delete<any>(`${url}/${id}`).toPromise();
    }

    /**
     * 
     */
    public listEnumPerfis(): Promise<any> {
        return this.http.get(`${url}/enum/perfis`).toPromise();
    }

    /**
     * 
     * @param id 
     * @param senhaAtual 
     * @param novaSenha 
     */
    public updateSenhaUsuario(id: number, senhaAtual: any, novaSenha: any): Promise<any> {
        let params = new HttpParams();
        params = params.set('senhaAtual', senhaAtual);
        params = params.set('novaSenha', novaSenha);
        
        return this.http.put(`${url}/alterar-senha/${id}`, params).toPromise();
    }    

    /**
     * 
     * @param nome 
     * @param perfil 
     */
    public listUsuariosByNomeAndPerfil(nome: string, perfil: Perfil): Promise<any> {
        let params = new HttpParams();
        params = params.set('nome', nome);
        params = params.set('perfil', perfil);

        return this.http.get(`${url}/perfil`, { params }).toPromise();
    }
}
