package com.sofy.kara.main.java.algorithm;

import com.sofy.kara.main.java.algorithm.dto.Agent;
import com.sofy.kara.main.java.algorithm.dto.AlgorithmRequestDto;
import com.sofy.kara.main.java.algorithm.dto.AlgorithmResponseDto;
import com.sofy.kara.main.java.algorithm.exceptions.AlgorithmException;
import com.sofy.kara.main.java.dto.Point;
import com.sofy.kara.main.java.dto.Project;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Класс, инкапсулирующий логику роевого алгоритма на
 * основе поведения пчел с разделением ролей
 * Creation date: 13.06.2019
 *
 * @author karavanova-sa
 */
public class Algorithm {

    /**
     * Кол-во разведчиков
     */
    private Integer numberOfScouts;

    /**
     * Кол-во рабочих для каждой лучшей группы
     */
    private static final Integer NUMBER_OF_WORKERS_FOR_BEST = 8;

    /**
     * Кол-во рабочих для каждой перспективной группы
     */
    private static final Integer NUMBER_OF_WORKERS_FOR_PROMISED = 8;

    /**
     * Кол-во лучших групп
     */
    private static final Integer NUMBER_OF_BEST = 10;

    /**
     * Кол-во перспективных групп
     */
    private static final Integer NUMBER_OF_PROMISED = 10;

    /**
     * Число итераций
     */
    private final Integer numberOfIteration;

    /**
     * Лучшие особи (пчелы)
     */
    private ArrayList<Point> bestPositions;

    /**
     * Перспективные особи (пчелы)
     */
    private ArrayList<Point> promisingPositions;

    /**
     * Смещение по координате x
     */
    private static final double DX = 0.5;

    /**
     * Смещение по координате y
     */
    private static final double DY = 0.1;

    /**
     * Объект с заданными пользователем параметрами
     */
    private AlgorithmRequestDto request;

    public Algorithm(AlgorithmRequestDto data) {
        this.request = data;
        numberOfIteration = request.getNumberOfIteration();
        numberOfScouts = request.getNumberOfScouts();
    }

    /**
     * Выполняет расчет по алгоритму
     * Сначала инициализируется рой(разведчики, рабочие)
     * Затем Разведчики отправляются в "исследование" на случайные позиции.
     * Результаты этого полета анализируются и выбираются лучшие и перспиктивные области
     * для отправки туда рабочих пчелок (по сути для уточнения решения).
     * Затем происходит обмен опытом (по сути поиск и запоминание лучшей позиции).
     * Затем снова отправляются разведчики в новый поиск.
     *
     * @return заполненный обект {@link AlgorithmResponseDto} результатов работы алгоритма
     * @throws AlgorithmException
     */
    public AlgorithmResponseDto execute() throws AlgorithmException {
        long start = System.nanoTime();
        ArrayList<Agent> bestPoints = new ArrayList();

        //Инициализация роя
        ArrayList<Agent> scouts;
        ArrayList<Agent> workersForBestPosition = initAgents(NUMBER_OF_WORKERS_FOR_BEST * NUMBER_OF_BEST);
        ArrayList<Agent> workersForPromisedPosition = initAgents(NUMBER_OF_WORKERS_FOR_PROMISED * NUMBER_OF_PROMISED);
        ArrayList<Agent> swarm = new ArrayList();

        for (int i = 0; i < numberOfIteration; i++) {
            //Разведка
            scouts = initAgents(numberOfScouts);
            swarm.addAll(sendInExploration(scouts));

            //Отбор лучших и перспективных областей
            sortAgents(swarm);
            bestPositions = selectionBestPositions(swarm);
            promisingPositions = selectionPromisingPositions(swarm);

            //Отправка работников в окресности позиций
            moveWorkers(bestPositions, workersForBestPosition, NUMBER_OF_WORKERS_FOR_BEST);
            moveWorkers(promisingPositions, workersForPromisedPosition, NUMBER_OF_WORKERS_FOR_PROMISED);

            swarm.clear();
            //агрегация опыта
            swarm.addAll(scouts);
            swarm.addAll(workersForBestPosition);
            swarm.addAll(workersForPromisedPosition);
            sortAgents(swarm);
            bestPoints.add(swarm.get(0));
        }

        return prepareResponse((System.nanoTime() - start) / 1000000000);
    }

    /**
     * Первоначальная инициализация агентов
     *
     * @return список разведчиков {@link ArrayList}
     */
    private ArrayList<Agent> initAgents(Integer numberOfAgents) {
        ArrayList<Agent> scouts = new ArrayList();
        for (int i = 0; i < numberOfAgents; i++) {
            scouts.add(new Agent());
        }
        return scouts;
    }

    /**
     * "Перемещает"  группами работников в окресности указанных позиций.
     *
     * @param positions   целевые позиции
     * @param workers     работники
     * @param numOfAgents кол-во работников, отправляемых в одну окресность
     * @throws AlgorithmException неверное соотношение кол-ва работников
     */
    private void moveWorkers(@NotNull ArrayList<Point> positions,
                             @NotNull ArrayList<Agent> workers,
                             Integer numOfAgents) throws AlgorithmException {
        if (numOfAgents * positions.size() != workers.size()) {
            throw new AlgorithmException("Неверно заданы параметры");
        }
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i * numOfAgents; j < numOfAgents * (i + 1); j++) {
                Point point = generatePointInArea(positions.get(i), DX, DY);
                workers.get(j).setCurrentPosition(point);
            }
        }
    }

    /**
     * Инициализирует разведчиков. Отправляет в "разведку" на сгенерированные координаты
     *
     * @param scouts список агентов-разведчиков {@link ArrayList}
     * @return список {@link ArrayList} агентов-разведчиков с установленными координатами {@link Point}
     */
    private ArrayList<Agent> sendInExploration(@NotNull ArrayList<Agent> scouts) {
        Random random = new Random();

        for (int i = 0; i < scouts.size(); i++) {
            int test = random.nextInt(10) - 10 / 2;
            scouts.get(i).setCurrentPosition(
                    new Point(
                            random.nextDouble() * test,
                            random.nextDouble() * (test / 10.)));
        }
        return scouts;
    }

    /**
     * Генерация точки {@link Point} в заданной окресности
     *
     * @param point точка - центр, от которого строится окресность
     * @param dx    смещение от центра по Ox
     * @param dy    смещение от центра по Oy
     * @return {@link Point} сгенерированная точка в заданной окресности
     */
    private Point generatePointInArea(Point point, double dx, double dy) {
        return new Point(
                generateNumberInInterval(point.getX() - dx, point.getX() + dx),
                generateNumberInInterval(point.getY() - dy, point.getY() + dy));
    }

    /**
     * Генерация псевдослучайного вещественного числа в заданном диапазоне
     *
     * @param start начало интервала
     * @param end   конец интервала
     * @return сгенерированное число
     */
    public static double generateNumberInInterval(double start, double end) {
        Random random = new Random();
        return start + ((random.nextDouble() * 100000) % (end - start));
    }

    /**
     * Отбор "Лучших позиций"
     *
     * @param agents список агентов
     * @return список {@link ArrayList} лучших позиций
     */
    private ArrayList<Point> selectionBestPositions(@NotNull ArrayList<Agent> agents) {
        ArrayList<Point> bestPoints = new ArrayList();
        for (int i = 0; i < NUMBER_OF_BEST; i++) {
            bestPoints.add(
                    new Point(
                            agents.get(i).getCurrentPosition().getX(),
                            agents.get(i).getCurrentPosition().getY()));
        }
        return bestPoints;
    }

    /**
     * Отбор "Перспективных позиций"
     *
     * @param agents список агентов
     * @return список {@link ArrayList} перспективных позиций
     */
    private ArrayList<Point> selectionPromisingPositions(@NotNull ArrayList<Agent> agents) {

        ArrayList<Point> promisingPoints = new ArrayList();
        for (int i = NUMBER_OF_BEST; i < NUMBER_OF_BEST + NUMBER_OF_PROMISED; i++) {
            promisingPoints.add(
                    new Point(
                            agents.get(i).getCurrentPosition().getX(),
                            agents.get(i).getCurrentPosition().getY()));
        }
        return promisingPoints;
    }

    /**
     * Сортировка агентов от лучшей позиции (лучшего(min) значения фитнесс-функции) к худшей
     *
     * @param agents список агентов
     */
    private void sortAgents(@NotNull ArrayList<Agent> agents) {
        agents.sort(new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                return getFitnessValue(o1).compareTo(getFitnessValue(o2));
            }
        });
    }

    /**
     * Сортировка точек {@link Point} от лучшей позиции
     * (лучшего(min) значения фитнесс-функции) к худшей
     *
     * @param points список агентов
     */
    private void sortPoints(ArrayList<Point> points) {
        points.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return getFitnessValue(new Agent(o1)).compareTo(getFitnessValue(new Agent(o2)));
            }
        });
    }

    /**
     * Вычисление значения фитнесс-функции
     *
     * @param agent агент
     * @return {@link Double} значение фитнесс-функции
     */
    private Double getFitnessValue(Agent agent) {
        double sum = 0;
        for (Project project : request.getList()) {
            double subtract;
            subtract = project.getDifficultRating() - getFunctionValue(agent, project.getCodeLength());
            sum += Math.pow(subtract, 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Вычисление модельной стоимости проекта
     *
     * @param agent      агент
     * @param lenghtCode длина кода
     * @return значение стоимости проекта
     */
    private double getFunctionValue(@NotNull Agent agent, Double lenghtCode) {
        return agent.getCurrentPosition().getX() * Math.pow(lenghtCode, agent.getCurrentPosition().getY());
    }

    /**
     * Заполняет объект-результат работы алгоритма
     *
     * @param duration длительность выполнения операции
     * @return заполненный объект результат {@link AlgorithmResponseDto}
     */
    private AlgorithmResponseDto prepareResponse(long duration) {
        sortPoints(bestPositions);
        double bestFitness = getFitnessValue(new Agent(bestPositions.get(0)));
        List<Point> modelData = fillModelData(bestPositions.get(0));
        return new AlgorithmResponseDto(bestPositions.get(0), modelData, duration, bestFitness);
    }

    private List<Point> fillModelData(Point point) {
        List<Point> list = new ArrayList<>();
        for (Project project : request.getList()) {
            list.add(new Point(project.getCodeLength().doubleValue(), getFunctionValue(new Agent(point), project.getCodeLength())));
        }
        return list;
    }

    private ArrayList<Agent> pointsToAgents(ArrayList<Point> points) {
        ArrayList<Agent> agents = new ArrayList<>();
        for (Point point : points) {
            agents.add(new Agent(point));
        }
        return agents;
    }
}