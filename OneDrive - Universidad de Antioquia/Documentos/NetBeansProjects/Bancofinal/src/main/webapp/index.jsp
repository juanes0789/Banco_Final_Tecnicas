<%-- 
    Document   : index
    Created on : 29/05/2024, 9:10:11 p. m.
    Author     : Juaan
--%>

<%@page import="java.util.List"%>
<%@page import="modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Banco Master</title>
        <link
            href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;700;900&display=swap"
            rel="stylesheet"
            />
        <link rel="stylesheet" href="build/css/tailwind.css" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

        <script src="https://cdn.jsdelivr.net/gh/alpine-collective/alpine-magic-helpers@0.5.x/dist/component.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.7.3/dist/alpine.min.js" defer></script>
    </head>
    <body>
        <div x-data="setup()" x-init="$refs.loading.classList.add('hidden'); setColors(color);" :class="{ 'dark': isDark}">
            <div class="flex h-screen antialiased text-gray-900 bg-gray-100 dark:bg-dark dark:text-light">




                <div class="flex-1 h-full overflow-x-hidden overflow-y-auto">
                    <!-- Navbar -->
                    <header class="relative bg-white dark:bg-darker">
                        <div class="flex items-center justify-between p-2 border-b dark:border-primary-darker">
                            <!-- Mobile menu button -->
                            <button
                                @click="isMobileMainMenuOpen = !isMobileMainMenuOpen"
                                class="p-1 transition-colors duration-200 rounded-md text-primary-lighter bg-primary-50 hover:text-primary hover:bg-primary-100 dark:hover:text-light dark:hover:bg-primary-dark dark:bg-dark md:hidden focus:outline-none focus:ring"
                                >
                                <span class="sr-only">Open main manu</span>
                                <span aria-hidden="true">
                                    <svg
                                        class="w-8 h-8"
                                        xmlns="http://www.w3.org/2000/svg"
                                        fill="none"
                                        viewBox="0 0 24 24"
                                        stroke="currentColor"
                                        >
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
                                    </svg>
                                </span>
                            </button>

                            <!-- Brand -->
                            <a
                                href="index.jsp"
                                class="inline-block text-2xl font-bold tracking-wider uppercase text-primary-dark dark:text-light"
                                >
                                Banco Master
                            </a>

                    </header>

                    <!-- Main content -->
                    <main>
                        <!-- Content header -->
                        <div class="flex items-center justify-between px-4 py-4 border-b lg:py-6 dark:border-primary-darker">
                            <h1 class="text-2xl font-semibold">Operaciones</h1>

                        </div>

                        <!-- Content -->
                        <div class="mt-2">
                            <!-- State cards -->
                            <div class="grid grid-cols-1 gap-8 p-4 lg:grid-cols-2 xl:grid-cols-4">
                                <form action="Transaccion.jsp" method="post">
                                    <div class="flex items-center justify-between p-4 bg-white rounded-md dark:bg-darker transition-colors duration-300 hover:bg-gray-100 dark:hover:bg-gray-800" onclick="this.parentNode.submit();">
                                        <div>
                                            <span class="text-xl font-semibold">Transacciones</span>
                                        </div>
                                        <div>
                                            <span>
                                                <svg
                                                    class="w-12 h-12 text-gray-300 dark:text-primary-dark"
                                                    xmlns="http://www.w3.org/2000/svg"
                                                    fill="none"
                                                    viewBox="0 0 24 24"
                                                    stroke="currentColor"
                                                    >
                                                <path
                                                    stroke-linecap="round"
                                                    stroke-linejoin="round"
                                                    stroke-width="2"
                                                    d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                                                    />
                                                </svg>
                                            </span>
                                        </div>
                                    </div>
                                </form>

                                <form action="registro.jsp" method="post">
                                    <!-- Users card -->
                                    <div class="flex items-center justify-between p-4 bg-white rounded-md dark:bg-darker transition-colors duration-300 hover:bg-gray-100 dark:hover:bg-gray-800" onclick="this.parentNode.submit();">
                                        <div>

                                            <span class="text-xl font-semibold">Registrar</span>

                                        </div>
                                        <div>
                                            <span>
                                                <svg class="w-12 h-12 text-gray-300 dark:text-primary-dark" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"/>
                                                </svg>
                                            </span>
                                        </div>
                                    </div>
                                </form>



                            </div>


                            <!-- Charts -->
                            <div class="grid grid-cols-1 p-4 space-y-8 lg:gap-8 lg:space-y-0 lg:grid-cols-3">
                                <!-- Bar chart card -->
                                <div class="col-span-2 bg-white rounded-md dark:bg-darker" x-data="{ isOn: false }">
                                    <!-- Card header -->
                                    <div class="flex items-center justify-between p-4 border-b dark:border-primary">
                                        <h4 class="text-lg font-semibold text-gray-500 dark:text-light">Tabla de Clientes y Cuentas</h4>
                                    </div>
                                    <!-- Table -->
                                    <div class="p-4">
                                        <div class="overflow-x-auto">
                                            <table class="w-full border-collapse border border-gray-200">
                                                <thead>
                                                    <tr>
                                                        <th class="px-4 py-2 bg-gray-200 border border-gray-200">Consecutivo</th>
                                                        <th class="px-4 py-2 bg-gray-200 border border-gray-200">Nombre</th>
                                                        <th class="px-4 py-2 bg-gray-200 border border-gray-200">Apellido</th>
                                                        <th class="px-4 py-2 bg-gray-200 border border-gray-200">Identificación</th>
                                                        <th class="px-4 py-2 bg-gray-200 border border-gray-200">Número de Cuenta</th>
                                                        <th class="px-4 py-2 bg-gray-200 border border-gray-200">Tipo de Cuenta</th>
                                                        <th class="px-4 py-2 bg-gray-200 border border-gray-200">Saldo  </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%-- Java code to fetch and display data from database --%>
                                                    <%@ page import="java.util.List" %>
                                                    <%@ page import="modelo.Cliente" %>
                                                    <%@ page import="modelo.Cuenta" %>
                                                    <%@ page import="dao.ClienteDAO" %>
                                                    <%@ page import="dao.CuentaDAO" %>
                                                    <%@ page import="configuracion.Conexion" %>
                                                    <%@ page import="java.sql.Connection" %>
                                                    <%@ page import="java.sql.PreparedStatement" %>
                                                    <%@ page import="java.sql.ResultSet" %>
                                                    <%@ page import="java.sql.SQLException" %>
                                                    <%@ page import="java.math.BigDecimal" %>
                                                    <%
                                                        List<Cliente> clientes = new ClienteDAO().obtenerClientesConCuentas();
                                                        for (Cliente cliente : clientes) {
                                                            Cuenta cuenta = cliente.getCuenta();
                                                    %>
                                                    <tr>
                                                        <td class="border px-4 py-2"><%= cliente.getConsecutivo()%></td>
                                                        <td class="border px-4 py-2"><%= cliente.getNombre1()%></td>
                                                        <td class="border px-4 py-2"><%= cliente.getApellido1()%></td>
                                                        <td class="border px-4 py-2"><%= cliente.getIdentificacion()%></td>
                                                        <td class="border px-4 py-2"><%= cuenta.getNumeroCuenta()%></td>
                                                        <td class="border px-4 py-2"><%= cuenta.getTipoCuenta()%></td>
                                                        <td class="border px-4 py-2"><%= cuenta.getSaldo()%></td>
                                                    </tr>
                                                    <% }%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Main footer -->


                            <!-- Panels -->

                            <!-- Settings Panel -->
                            <!-- Backdrop -->




                        </div>
                </div>

                <!-- All javascript code in this project for now is just for demo DON'T RELY ON IT  -->
                <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.bundle.min.js"></script>
                <script src="build/js/script.js"></script>
                <script>


                                        const setup = () => {
                                            const getTheme = () => {
                                                if (window.localStorage.getItem('dark')) {
                                                    return JSON.parse(window.localStorage.getItem('dark'))
                                                }

                                                return !!window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches
                                            }

                                            const setTheme = (value) => {
                                                window.localStorage.setItem('dark', value)
                                            }

                                            const getColor = () => {
                                                if (window.localStorage.getItem('color')) {
                                                    return window.localStorage.getItem('color')
                                                }
                                                return 'cyan'
                                            }

                                            const setColors = (color) => {
                                                const root = document.documentElement
                                                root.style.setProperty('--color-primary', `var(--color-${color})`)
                                                root.style.setProperty('--color-primary-50', `var(--color-${color}-50)`)
                                                root.style.setProperty('--color-primary-100', `var(--color-${color}-100)`)
                                                root.style.setProperty('--color-primary-light', `var(--color-${color}-light)`)
                                                root.style.setProperty('--color-primary-lighter', `var(--color-${color}-lighter)`)
                                                root.style.setProperty('--color-primary-dark', `var(--color-${color}-dark)`)
                                                root.style.setProperty('--color-primary-darker', `var(--color-${color}-darker)`)
                                                this.selectedColor = color
                                                window.localStorage.setItem('color', color)
                                                //
                                            }




                                            return {
                                                loading: true,
                                                isDark: getTheme(),
                                                toggleTheme() {
                                                    this.isDark = !this.isDark
                                                    setTheme(this.isDark)
                                                },
                                                setLightTheme() {
                                                    this.isDark = false
                                                    setTheme(this.isDark)
                                                },
                                                setDarkTheme() {
                                                    this.isDark = true
                                                    setTheme(this.isDark)
                                                },
                                                color: getColor(),
                                                selectedColor: 'cyan',
                                                setColors,
                                                toggleSidbarMenu() {
                                                    this.isSidebarOpen = !this.isSidebarOpen
                                                },
                                                isSettingsPanelOpen: false,
                                                openSettingsPanel() {
                                                    this.isSettingsPanelOpen = true
                                                    this.$nextTick(() => {
                                                        this.$refs.settingsPanel.focus()
                                                    })
                                                },
                                                isNotificationsPanelOpen: false,
                                                openNotificationsPanel() {
                                                    this.isNotificationsPanelOpen = true
                                                    this.$nextTick(() => {
                                                        this.$refs.notificationsPanel.focus()
                                                    })
                                                },
                                                isSearchPanelOpen: false,
                                                openSearchPanel() {
                                                    this.isSearchPanelOpen = true
                                                    this.$nextTick(() => {
                                                        this.$refs.searchInput.focus()
                                                    })
                                                },
                                                isMobileSubMenuOpen: false,
                                                openMobileSubMenu() {
                                                    this.isMobileSubMenuOpen = true
                                                    this.$nextTick(() => {
                                                        this.$refs.mobileSubMenu.focus()
                                                    })
                                                },
                                                isMobileMainMenuOpen: false,
                                                openMobileMainMenu() {
                                                    this.isMobileMainMenuOpen = true
                                                    this.$nextTick(() => {
                                                        this.$refs.mobileMainMenu.focus()
                                                    })
                                                },
                                                updateBarChart

                                            }
                                        }
                </script>

                </body>
                </html>
