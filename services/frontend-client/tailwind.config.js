/** @type {import('tailwindcss').Config} */

export const content = [
    "./src/**/*.{html,ts}",
];

export const theme = {
    extend: {
        colors: {
            "primary": "#0df4e2",
            "accent": "#e20d74",
            "warn": "#e20d0d",
            "main": "#201b31",
            "secondary": "#2c2745",
            "tertiary": "#413955"
        },
        screens: {
            "ms": "320px",
            "mm": "320px",
            "ml": "425px",
            "tb": "768px",
            "lm": "1024px",
            "ll": "1440px"
        }
    }
};

export const plugins = [];
