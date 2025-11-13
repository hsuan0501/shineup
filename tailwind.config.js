/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // 深色模式
        'dark-bg': '#0f172a',
        'dark-card': '#1e293b',
        'dark-border': '#334155',
        'dark-text': '#f1f5f9',
        'dark-text-secondary': '#cbd5e1',
        
        // 淺色模式
        'light-bg': '#f5f3ff',
        'light-card': '#faf9fc',
        'light-border': '#e9d5ff',
        'light-text': '#1e1b4b',
        'light-text-secondary': '#4c1d95',
        
        // 強調色
        'primary-purple': '#8b5cf6',
        'primary-blue': '#3b82f6',
        'primary-light': '#a78bfa',
      },
      backgroundImage: {
        // 淺色漸層背景
        'light-gradient': 'linear-gradient(135deg, #ffffff 0%, #f3e8ff 100%)',
        'light-gradient-subtle': 'linear-gradient(135deg, #faf9fc 0%, #f3e8ff 100%)',
        
        // 深色漸層背景
        'dark-gradient': 'linear-gradient(135deg, #0f172a 0%, #1e1b4b 50%, #1e3a8a 100%)',
        'dark-gradient-card': 'linear-gradient(135deg, #1e293b 0%, #1e1b4b 100%)',
      },
      animation: {
        'float': 'float 6s ease-in-out infinite',
        'glow': 'glow 2s ease-in-out infinite',
        'pulse-glow': 'pulseGlow 2s cubic-bezier(0.4, 0, 0.6, 1) infinite',
        'slide-in': 'slideIn 0.5s ease-out',
        'fade-in': 'fadeIn 0.3s ease-out',
      },
      keyframes: {
        float: {
          '0%, 100%': { transform: 'translateY(0px)' },
          '50%': { transform: 'translateY(-20px)' },
        },
        glow: {
          '0%, 100%': { boxShadow: '0 0 20px rgba(139, 92, 246, 0.5)' },
          '50%': { boxShadow: '0 0 30px rgba(139, 92, 246, 0.8)' },
        },
        pulseGlow: {
          '0%, 100%': { opacity: '1' },
          '50%': { opacity: '0.7' },
        },
        slideIn: {
          from: { transform: 'translateX(-100%)' },
          to: { transform: 'translateX(0)' },
        },
        fadeIn: {
          from: { opacity: '0' },
          to: { opacity: '1' },
        },
      },
      boxShadow: {
        'glow': '0 0 20px rgba(139, 92, 246, 0.5)',
        'glow-lg': '0 0 30px rgba(139, 92, 246, 0.7)',
      },
    },
  },
  plugins: [],
  darkMode: 'class',
}