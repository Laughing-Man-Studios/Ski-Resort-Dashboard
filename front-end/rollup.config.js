// Import rollup plugins
import resolve from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';
import typescript from '@rollup/plugin-typescript';

export default {
    input: [
        'typescript/sidebar.ts'
    ],
    plugins: [
      // Resolve bare module specifiers to relative paths
      resolve(),
      typescript(),
      // Minify JS
      terser()
    ],
    output: {
      dir: '../public/javascripts',
      sourcemap: true
    },
    preserveEntrySignatures: 'strict',
  };