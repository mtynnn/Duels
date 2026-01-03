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

---

## 📜 Lista de Comandos

### ⚔️ Duelos (Jugadores)
El comando principal para interactuar con el sistema de duelos.

*   `/duel [jugador]` - Envía una solicitud de duelo a un jugador. Si tienes "Inventario Propio" habilitado, se mostrará una advertencia.
*   `/duel [jugador]` - Envía una solicitud de duelo a un jugador. Si tienes "Inventario Propio" habilitado, se mostrará una advertencia.
*   `/duel [jugador] [dinero]` - Envía una solicitud de duelo apostando una cantidad de dinero.
*   `/duel [jugador] [dinero] [true|false] [-]` - (Avanzado) Envía solicitud definiendo apuesta, apuesta de items (true/false) y ` - ` para activar inventario propio.
*   `/duel accept [jugador]` - Acepta la solicitud de duelo de un jugador.
*   `/duel deny [jugador]` - Rechaza la solicitud de duelo de un jugador.
*   `/duel cancelrequest [jugador]` - Cancela una solicitud de duelo que hayas enviado.
*   `/duel draw` - Envía una solicitud de empate (o acepta una existente). Solo un uso por partida.
*   `/duel drawdeny` - Rechaza una solicitud de empate recibida.
*   `/duel stats [jugador]` - Muestra tus estadísticas o las de otro jugador.
*   `/duel top` - Muestra el ranking de los mejores jugadores (wins/rating).
*   `/duel toggle` - Activa o desactiva la recepción de solicitudes de duelo.
*   `/duel togglemessage` - Activa o desactiva los mensajes globales de victorias/derrotas.
*   `/duel viewrequest [jugador]` - Muestra los detalles de una solicitud recibida (GUI).
*   `/duel inventory [ID]` - (Admin/Debug) Permite ver un inventario guardado.

### 🛡️ Party (Grupos)
Comandos para gestionar grupos y pelear en equipo. Alias: `/duelparty`, `/dp`.

*   `/duelparty create` - Crea un nuevo grupo (Party).
*   `/duelparty disband` - Disuelve tu grupo actual.
*   `/duelparty invite [jugador]` - Invita a un jugador a tu grupo.
*   `/duelparty accept [jugador]` - Acepta una invitación de grupo.
*   `/duelparty kick [jugador]` - Expulsa a un miembro de tu grupo.
*   `/duelparty leave` - Sal del grupo actual.
*   `/duelparty list` - Muestra la lista de miembros de tu grupo.
*   `/duelparty transfer [jugador]` - Transfiere el liderazgo del grupo a otro miembro.
*   `/duelparty chat` - Activa/Desactiva el chat exclusivo de grupo.
*   `/duelparty friendlyfire` - Activa/Desactiva el fuego amigo en el grupo.

### ⏳ Colas (Queues)
Sistema de emparejamiento automático. Alias: `/duelqueue`, `/dq`.

*   `/duelqueue join [nombre-cola]` - Únete a una cola de emparejamiento (ej. `NoDebuff`).
*   `/duelqueue leave` - Sal de la cola actual.

### 🎒 Kits (Jugadores)
Gestión de equipamiento. Alias: `/duelkit`, `/dk`.

*   `/duelkit` - Abre el menú de selección de kits.
*   `/duelkit edit` - Abre el editor de kits (si tienes permiso).
*   `/duelkit save` - Guarda tu inventario actual como un kit personal (si está habilitado).

### 👁️ Espectador
*   `/spectate [jugador]` - Entra en modo espectador para ver el duelo de un jugador.

---

### 🔧 Administración (`/duels`)
Comandos para configurar el plugin, arenas y más. Requiere permiso `duels.admin`.

#### Gestión de Arenas
*   `/duels create [nombre]` - Crea una nueva arena.
*   `/duels delete [nombre]` - Elimina una arena existente.
*   `/duels bind [arena] [kit]` - Vincula una arena a un kit específico (solo se podrá usar con ese kit).
*   `/duels set [arena] [1|2]` - Establece los puntos de aparición (spawn 1 y spawn 2) de la arena.
*   `/duels setbound [arena] [1|2]` - Establece los límites de la arena (requiere selección con hacha de WorldEdit).
*   `/duels savesnapshot [arena]` - Guarda el estado actual de la arena (bloques) para restaurarlo al finalizar los duelos.
*   `/duels regenerate [arena]` - Fuerza la regeneración de una arena desde su snapshot.
*   `/duels enable [arena]` - Habilita una arena para su uso.
*   `/duels disable [arena]` - Deshabilita una arena (modo mantenimiento).
*   `/duels setarenaitem [arena]` - Establece el item que tienes en la mano como icono de la arena.

#### Gestión de Colas (Queues)
*   `/duels createqueue [nombre] [apuesta] [tamaño] [-|kit]` - Crea una cola. Usa `-` para sin kit (Combate normal). Tamaño es 1 para 1v1.
*   `/duels deletequeue [nombre]` - Elimina una cola.
*   `/duels addsign [nombre] [apuesta] [tamaño] [-|kit]` - (Mirando un cartel) Crea una cola (si no existe) y convierte el cartel en uno de "Join Queue".

#### Gestión de Kits (Admin)
*   `/duels savekit [nombre] [-o]` - Guarda tu inventario actual como un kit del servidor (usa `-o` para sobrescribir).
*   `/duels loadkit [nombre]` - Carga un kit del servidor a tu inventario.
*   `/duels deletekit [nombre]` - Elimina un kit del servidor.
*   `/duels setitem [nombre]` - Establece el item en tu mano como icono del kit.
*   `/duels setkitlobby` - Establece la ubicación del "Editor de Kits".

#### Otros
*   `/duels setlobby` - Establece el lobby principal del plugin.
*   `/duels reload` - Recarga la configuración y los archivos de idioma.
*   `/duels help` - Muestra la ayuda de comandos de administración.
*   `/duels setrating [jugador] [-|kit] [cantidad]` - Modifica el rating. Usa `-` para rating general.
*   `/duels resetrating [jugador] [-|all|kit]` - Reinicia el rating. Usa `-` para general, `all` para todos.

---

## Permisos

| Permiso | Descripción |
| :--- | :--- |
| `duels.duel` | Permitir usar el comando `/duel`. |
| `duels.stats` | Ver estadísticas propias. |
| `duels.stats.others` | Ver estadísticas de otros. |
| `duels.toggle` | Usar `/duel toggle` y `/duel togglemessage`. |
| `duels.top` | Ver el top ranking. |
| `duels.spectate` | Espectar duelos. |
| `duels.admin` | Acceso a comandos de administración `/duels`. |
| `duels.kits.*` | Acceso a todos los kits. |
| `duels.kits.[nombre]` | Acceso a un kit específico. |
| `duels.use.own-inventory` | Permitir duelos con inventario propio. |
| `duels.use.money-betting` | Permitir apostar dinero. |
| `duels.use.item-betting` | Permitir apostar items. |

---

## 🧩 Placeholders (PlaceholderAPI)
Puedes usar estos _placeholders_ en cualquier plugin que soporte PlaceholderAPI (como scoreboards, chats, menús, etc.). El identificador base es `%duels_...%`.

### Estadísticas de Jugador
| Placeholder | Descripción | Ejemplo de Uso |
| :--- | :--- | :--- |
| `%duels_wins%` | Cantidad total de victorias. | `¡Llevas %duels_wins% ganadas!` |
| `%duels_losses%` | Cantidad total de derrotas. | `Has perdido %duels_losses% veces.` |
| `%duels_wlr%` (o `wl_ratio`) | Proporción victorias/derrotas. | `K/D: %duels_wlr%` |
| `%duels_rating_avg%` | Promedio del rating (ELO) considerando todos los kits y el sin kit. | `Rating promedio: %duels_rating_avg%` |
| `%duels_rating_-`% | Rating (ELO) general del jugador. | `Tu ELO: %duels_rating_-%` |
| `%duels_rating_[kit]%` | Rating específico de un kit. | `ELO en NoDebuff: %duels_rating_NoDebuff%` |

### Estado y Configuración
| Placeholder | Descripción |
| :--- | :--- |
| `%duels_can_request%` | `true` si puedes enviar duelos, `false` si no. |
| `%duels_setting_requests%` | `true` si tienes activada la recepción de solicitudes. |
| `%duels_setting_messages%` | `true` si tienes activados los mensajes de duelo. |

### Colas (Queues)
Muestra información sobre jugadores esperando o jugando en colas públicas.
_En `[cola]`, usa el nombre exacto de la cola o del kit._

| Placeholder | Descripción |
| :--- | :--- |
| `%duels_getplayersinqueue_[cola]%` | Jugadores esperando en esa cola. |
| `%duels_getplayersplayinginqueue_[cola]%` | Jugadores actualmente en partida de esa cola. |

### En Partida (Match)
Estos placeholders solo funcionan mientras el jugador está **dentro de un duelo** (jugando o espectando).

| Placeholder | Descripción |
| :--- | :--- |
| `%duels_match_duration%` | Duración actual del duelo (formato 00:00). |
| `%duels_match_kit%` | Nombre del kit que se está jugando. |
| `%duels_match_arena%` | Nombre de la arena actual. |
| `%duels_match_bet%` | Cantidad apostada. |
| `%duels_match_rating%` | Rating del jugador para el kit actual. |
| `%duels_match_opponent%` | Nombre del oponente. |
| `%duels_match_opponent_health%` | Vida del oponente (en corazones/puntos). |
| `%duels_match_opponent_ping%` | Ping del oponente. |
| `%duels_match_opponent_rating%` | Rating del oponente para el kit actual. |

---
*Desarrollado para ValerinSMP por Antigravity.*
