import {Injectable} from "@angular/core";

@Injectable()
export class ErrorHandler {
    public message: string = null;

    public handle(func: any) {
        try {
            func();
        } catch (e) {
            this.message = e;
            setTimeout(() => {
                this.message = null;
            }, 5000);
        }
    }
}
