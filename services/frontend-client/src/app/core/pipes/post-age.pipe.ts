import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'postAge',
    standalone: true
})
export class PostAgePipe implements PipeTransform {
    transform(value: Date): string {
        if (!value) {
            return "";
        }

        const units: string[] = ['y', 'm', 'd', 'h', 'm', 's'];
        const now: Date = new Date();
        const differences: number[] = [
            now.getFullYear() - value.getFullYear(),
            now.getMonth() - value.getMonth(),
            now.getDate() - value.getDate(),
            now.getHours() - value.getHours(),
            now.getMinutes() - value.getMinutes(),
            now.getSeconds() - value.getSeconds()
        ];

        for (let i: number = 0; i < differences.length; i++) {
            if (differences[i] > 0) {
                return `${ differences[i] } ${ units[i] }`;
            }
        }
        return "now";
    }
}
