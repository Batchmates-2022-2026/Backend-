package com.HaritMitraBack.mitra.service.Scheduler;

import com.HaritMitraBack.mitra.dto.NotificationMessage;
import com.HaritMitraBack.mitra.model.User;
import com.HaritMitraBack.mitra.repository.UserRepository;
import com.HaritMitraBack.mitra.service.*;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WeatherScheduler {
private final RabbitProducer rabbitProducer;
    private final WeatherService weatherService;
    private final ActivityService activityService;
    private final UserRepository userRepository;
    private final EngagementService engagementService;
    private final FirebaseService firebaseService;

    // ✅ CONSTRUCTOR (NO @Autowired needed)
    public WeatherScheduler(WeatherService weatherService,
                            ActivityService activityService,
                            UserRepository userRepository,
                            EngagementService engagementService,
                            FirebaseService firebaseService,
                            RabbitProducer rabbitProducer) {

        this.weatherService = weatherService;
        this.activityService = activityService;
        this.userRepository = userRepository;
        this.engagementService = engagementService;
        this.firebaseService = firebaseService;
        this.rabbitProducer = rabbitProducer;
    }

    // ✅ ENGAGEMENT ALERT (INACTIVITY)
    @Scheduled(fixedRate = 300000) // 5 min
    public void sendEngagementAlerts() {

        List<User> users = userRepository.findAll();
        long now = System.currentTimeMillis();

        for (User user : users) {

            if (user.getFcmToken() == null) continue;
            if (user.getLastActiveTime() == null) continue;

            long diff = now - user.getLastActiveTime();

            if (diff > 600000) { // 10 min inactive

                String msg = engagementService.getRandomMessage();


                NotificationMessage message = new NotificationMessage(
                        user.getFcmToken(),
                        "👋 Hey!",
                        msg

                );
                rabbitProducer.sendNotification(
                        message

                );
            }
        }
    }

    // ✅ WEATHER ALERTS
    @Scheduled(fixedRate = 60000) // 1 min
    public void checkWeather() {

        System.out.println("⏰ Checking weather...");

        List<User> users = userRepository.findByWeatherAlertEnabledTrue();

        for (User user : users) {

            if (user.getCity() == null) continue;

            Map<String, Object> data = weatherService.getWeather(user.getCity());

            if (data == null) continue;

            String condition = data.get("condition").toString().toLowerCase();
            double temp = Double.parseDouble(data.get("temperature").toString());

            // 🌧️ Rain Alert
            if (Boolean.TRUE.equals(user.getRainAlert()) && condition.contains("rain")) {


                NotificationMessage msg = new NotificationMessage(
                        user.getFcmToken(),
                        "🌧 Rain Alert",
                        "Rain expected in " + user.getCity()
                );
                rabbitProducer.sendNotification(msg);


                activityService.log(user.getEmail(), "RAIN_ALERT", "Rain expected");
            }

            // 🔥 Heat Alert
            if (Boolean.TRUE.equals(user.getHeatAlert()) && temp >= 40) {

                firebaseService.sendNotification(
                        user.getFcmToken(),
                        "🔥 Heat Alert",
                        "High temperature in " + user.getCity()
                );

                activityService.log(user.getEmail(), "HEAT_ALERT", "Heat wave");
            }

            // ⛈️ Storm Alert
            if (Boolean.TRUE.equals(user.getStormAlert()) &&
                    (condition.contains("storm") || condition.contains("thunder"))) {

                firebaseService.sendNotification(
                        user.getFcmToken(),
                        "⛈ Storm Alert",
                        "Storm warning in " + user.getCity()
                );

                activityService.log(user.getEmail(), "STORM_ALERT", "Storm alert");
            }

            // 🌧️ Monsoon Alert
            if (Boolean.TRUE.equals(user.getMonsoonAlert()) && condition.contains("rain")) {

                firebaseService.sendNotification(
                        user.getFcmToken(),
                        "🌧 Monsoon Alert",
                        "Heavy rain risk in " + user.getCity()
                );

                activityService.log(user.getEmail(), "MONSOON_ALERT", "Monsoon alert");
            }
        }
    }
}