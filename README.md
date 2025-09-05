# 🏦 Bank Management System

Un système de gestion bancaire simple développé en Java avec interface graphique Swing et base de données MySQL.

## 📋 Description

Ce projet simule un système bancaire basique permettant aux utilisateurs de :
- Se connecter avec leur numéro de carte et code PIN
- Créer un nouveau compte bancaire
- Effectuer des dépôts et retraits
- Consulter l'historique des transactions
- Voir les informations du compte

Le système inclut également une interface d'administration pour la gestion des comptes.

## 🚀 Fonctionnalités

- **Authentification sécurisée** : Connexion avec numéro de carte et PIN
- **Gestion de comptes** : Création et gestion de comptes bancaires
- **Transactions** : Dépôts et retraits d'argent
- **Historique** : Suivi des transactions effectuées
- **Interface admin** : Gestion administrative des comptes
- **Sécurité** : Hachage des mots de passe avec BCrypt

## 🛠️ Technologies utilisées

- **Java** : Langage principal
- **Swing** : Interface graphique
- **MySQL** : Base de données
- **JDBC** : Connecteur base de données
- **JCalendar** : Composant calendrier

## 📁 Structure du projet

```
src/bank/management/system/
├── Login.java           # Interface de connexion
├── Signup.java          # Inscription (étape 1)
├── Signup2.java         # Inscription (étape 2)
├── Accueil.java         # Page d'accueil après connexion
├── Deposit.java         # Interface de dépôt
├── Withdraw.java        # Interface de retrait
├── TransactionHistory.java # Historique des transactions
├── AccountInfo.java     # Informations du compte
├── AdminInterface.java  # Interface d'administration
├── sqlcon.java          # Connexion base de données
├── BCrypt.java          # Utilitaire de hachage
└── PasswordUtil.java    # Utilitaires mot de passe
```

## 🗄️ Base de données

Le projet utilise une base de données MySQL avec les tables principales :
- `account` : Informations des comptes bancaires
- `signup` : Données d'inscription des utilisateurs
- `bank` : Transactions bancaires

## 🔧 Installation

1. Cloner le projet
2. Importer les librairies JAR depuis le dossier `lib/`
3. Configurer la base de données MySQL avec le script `Banksystem (2).sql`
4. Compiler et exécuter `Login.java`

## 💡 Utilisation

1. Lancer l'application
2. Se connecter avec un numéro de carte et PIN existants ou créer un nouveau compte
3. Utiliser l'interface pour effectuer des transactions bancaires

---

*Projet développé en Java - Interface bancaire simple et fonctionnelle*
