# Duels Plugin

Un sistema de duelos avanzado y completo para servidores de Minecraft. Soporta duelos 1v1, duelos de party, apuestas de dinero/items, y múltiples arenas.

## Características Principales

*   **Duelos 1v1 y Party vs Party**: Reta a jugadores individuales o organiza batallas entre equipos completos.
*   **Inventario Propio con Riesgo**: Modo especial donde los jugadores pelean con sus propios items. Incluye advertencias configurables y posibilidad de perder items al morir.
*   **Sistema de Apuestas**: Soporte para apostar dinero (requiere Vault) o items del inventario.
*   **Arenas Multi-Mundo**: Crea arenas en cualquier mundo. Soporte para regeneración automática de arenas (Snapshot).
*   **Kits Personalizados**: Crea, edita y guarda kits directamente desde el juego.
*   **Colas de Emparejamiento (Queues)**: Únete a colas automatizadas para encontrar oponentes rápidamente.
*   **Espectador**: Sistema de espectador robusto para ver partidas en curso.
*   **Integración**: Soporte para WorldGuard, Vault, y PlaceholderAPI.

## Comandos

### Comandos Generales
*   `/duel [jugador]` - Envía una solicitud de duelo o abre la GUI de configuración.
*   `/duel [jugador] [cantidad]` - Envía solicitud con apuesta de dinero.
*   `/duel accept [jugador]` - Acepta una solicitud.
*   `/duel deny [jugador]` - Rechaza una solicitud.
*   `/duel cancelrequest [jugador]` - Cancela una solicitud enviada.
*   `/duel stats [jugador]` - Ver estadísticas.
*   `/duel top` - Ver el ranking de mejores jugadores.
*   `/duel toggle` - Activa/Desactiva recibir solicitudes de duelo.
*   `/duel togglemessage` - Activa/Desactiva los anuncios globales de victorias.

### Gestión de Arenas
*   `/duel create [nombre]` - Crea una nueva arena.
*   `/duel delete [nombre]` - Elimina una arena.
*   `/duel set [nombre] [1|2]` - Establece los puntos de aparición (spawn 1 y 2).
*   `/duel setbound [nombre] [1|2]` - Establece los límites de la arena (requiere WorldEdit/FAWE).
*   `/duel savesnapshot [nombre]` - Guarda el estado de la arena para restaurarlo tras cada duelo.
*   `/duel toggle [nombre]` - Habilita o deshabilita una arena específica.

### Gestión de Kits
*   `/kit create [nombre]` - Crea un kit con tu inventario actual.
*   `/kit load [nombre]` - Carga un kit a tu inventario.
*   `/kit delete [nombre]` - Elimina un kit.
*   `/kit setitem [nombre]` - Define el item icono del kit en el menú.

### Party
*   `/party create` - Crea un grupo.
*   `/party invite [jugador]` - Invita a alguien.
*   `/party accept [jugador]` - Acepta invitación.
*   `/party kick [jugador]` - Expulsa un miembro.
*   `/party chat` - Chat exclusivo del grupo.

## Permisos

| Permiso | Descripción |
| :--- | :--- |
| `duels.duel` | Permitir usar el comando `/duel`. |
| `duels.stats` | Ver estadísticas propias. |
| `duels.stats.others` | Ver estadísticas de otros. |
| `duels.toggle` | Usar `/duel toggle` y `/duel togglemessage`. |
| `duels.top` | Ver el top ranking. |
| `duels.spectate` | Espectar duelos. |
| `duels.admin` | Acceso a comandos de administración (crear arenas, reload, etc). |
| `duels.kits.*` | Acceso a todos los kits. |
| `duels.use.own-inventory` | Permitir duelos con inventario propio. |
| `duels.use.money-betting` | Permitir apostar dinero. |
| `duels.use.item-betting` | Permitir apostar items. |

## Placeholders (PlaceholderAPI)
Variables útiles para scoreboards o chat:

*   `%duels_wins%` - Victorias totales.
*   `%duels_losses%` - Derrotas totales.
*   `%duels_rating%` - Rating global.
*   `%duels_setting_requests%` - Estado de solicitudes (`true`/`false`).
*   `%duels_setting_messages%` - Estado de mensajes globales (`true`/`false`).

## Instalación
1.  Arrastra el `.jar` a la carpeta `plugins`.
2.  (Opcional) Instala **WorldEdit** o **FastAsyncWorldEdit (FAWE)** para usar la regeneración de arenas.
3.  (Opcional) Instala **Vault** y un plugin de economía para las apuestas.
4.  Reinicia el servidor.

## Configuración Recomendada
Si usas "Inventario Propio", asegúrate de que los jugadores entiendan que **pueden perder sus items**. El plugin incluye advertencias, pero es responsabilidad del servidor configurarlo.

---
*Desarrollado para ValerinSMP por Antigravity.*
