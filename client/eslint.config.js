import js from '@eslint/js'
import reactPlugin from 'eslint-plugin-react'
import prettierConfig from 'eslint-config-prettier'

export default [
    js.configs.recommended,
    {
        files: ['**/*.{js,jsx,mjs,cjs,ts,tsx}'],
        plugins: {
            react: reactPlugin,
        },
        languageOptions: {
            ecmaVersion: 'latest',
            sourceType: 'module',
            parserOptions: {
                ecmaFeatures: {
                    jsx: true,
                },
            },
        },
        rules: {
            ...reactPlugin.configs.recommended.rules,
            'react/react-in-jsx-scope': 'off',
            'no-unused-vars': 'warn',
            'no-console': 'warn',
        },
        settings: {
            react: {
                version: 'detect',
            },
        },
    },
    prettierConfig,
]
